import analizator.Action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Dominik on 8.11.2016..
 */
public class GSA {
    public static Grammar grammar;
    public static Map<Symbol, Set<Symbol>> first;

    public static List<Set<Item>> canonical;

    public static Map<Pair, Action> actions;
    public static Map<Pair, Integer> goTos;

    public static void main(String[] args) throws IOException {
        //učitavanje gramatike i inicijalizacija parametara
        grammar = new GSAParser(readLines()).getGrammar().getAugmentedGrammar();
        first = new HashMap<>();
        canonical = new ArrayList<>();
        actions = new HashMap<>();
        goTos = new HashMap<>();

        //računamo skup Započni za svaki znak gramatike
        computeFirst();

        //kanonski skupovi LR(1) parsera
        canonical();

        //definiranje akcija (pomakni/reduciraj/prihvati)
        defineActions();

        //definiranje NovogStanja
        defineGoTos();

        //spremanje podataka za serijalizaciju
        SAData data = new SAData(grammar, first, actions, goTos);
        data.writeData(SA.DATA_PATH);
    }

    private static void defineGoTos() {
        /**
         * Algoritam: "Za svaki nezavršni znak gramatike A i skup stavki iz kanonskog skupa I (koji čine
         * jedno stanje)  provjeriti da li u kanonskom skupu postoji skup (i stanje) definiran relacijom GOTO(I, A).
         * Ako postoji radimo novu akciju NovoStanje(staroStanje, A, NovoStanje)
         */
        int size = canonical.size();
        for (Symbol.Nonterminal nonterminal : grammar.getNonterminals()) {
            for (int i = 0; i < size; i++) {
                Set<Item> items = canonical.get(i);

                Set<Item> goTo = goTo(items, nonterminal);
                int index = canonical.indexOf(goTo);

                if (index == -1) {
                    continue;
                }

                goTos.put(new Pair(i, nonterminal), index);
            }
        }
    }

    private static void defineActions() {
        /**
         * Za svako stanje (i) i završni znak a određuje se akcija na sljedeći način:
         *  1.) Izaberi akciju pomakni
         *  2.) Izabrei akciju reduciraj (moglo se de dodati i bez jednoznačnosti, no dodano zbog zadatka)
         *  3.) Izaberi akciju prihvati
         *  4.) U slučaju da nije izabrana neka od gornjih akcija, izabire se akcija "PrijaviPogrešku"
         */
        int size = canonical.size();
        for (Symbol.Terminal terminal : grammar.getTerminals()) {
            for (int i = 0; i < size; i++) {
                Set<Item> items = canonical.get(i);

                boolean isShift = checkShiftAction(items, terminal, i);

                boolean isReduce = checkReduceAction(items, terminal, i);
                if (isReduce) {
                    if (isShift) {
                        System.err
                                .println("Pomakni/reduciraj nejednoznačnost. Stanje: " + i + ", terminal: " + terminal);
                    }
                }
                if (isShift || isReduce) {
                    continue;
                }

                if (terminal.equals(Symbol.Terminal.END_MARKER)) {
                    boolean isAccepted = checkAcceptAction(items, i);
                    if (isAccepted) {
                        continue;
                    }
                }

                errorAction(terminal, i);
            }
        }
    }

    /**
     * Akcija izabrana  u slučaju da uvjeti za prethodne akcije nisu zadovoljeni
     *
     * @param terminal završni znak
     * @param position stanje
     */
    private static void errorAction(Symbol.Terminal terminal, int position) {
        actions.put(new Pair(position, terminal), new Action(Action.ActionEnum.ERROR, -1));
    }

    private static boolean checkAcceptAction(Set<Item> items, int position) {
        /**
         * Izaberi akciju prihvati u slučaju da postoji stavka u skupu za koji vrijedi:
         * [S'->S×, $] (ovdje je $ kraj niza), a pročitani znak je također $
         */
        boolean contains = items.stream().anyMatch(it -> it.getProduction().equals(grammar.getProduction(0)) &&
                                                         it.getTerminal().equals(Symbol.Terminal.END_MARKER) &&
                                                         it.dotAtEnd());
        if (contains) {
            actions.put(new Pair(position, Symbol.Terminal.END_MARKER), new Action(Action.ActionEnum.ACCEPT, 0));

            return true;
        }

        return false;
    }

    private static boolean checkShiftAction(Set<Item> items, Symbol.Terminal terminal, int position) {
        /**
         * Izaberi akciju pomakni u slučaju da za neko stanje (i) i za simbol a vrijedi:
         * 1. [A -> alpha × a beta, b]
         * 2. GOTO(i, a) je član kakonskog skupa
         */
        boolean contains = items.stream().anyMatch(it -> it.dotBefore(terminal));

        if (contains) {
            Set<Item> goTo = goTo(items, terminal);
            int index = canonical.indexOf(goTo);

            if (index == -1) {
                return false;
            }

            actions.put(new Pair(position, terminal), new Action(Action.ActionEnum.SHIFT, index));
            return true;
        }

        return false;
    }

    private static boolean checkReduceAction(Set<Item> items, Symbol.Terminal terminal, int position) {
        /**
         * Akcija reduciraj je definirana u slučaju kada za stanje i, završni znak a vrijedi:
         * 1. U skupu postoji stavka oblika [A -> alpha×, a]
         * 2. Lijeva strana produkcije nije S'
         * Definira se akcija Reduciraj(i, a) = j, gdje je j redni broj produkcije u gramatici
         *
         * Napomena: Ovdje izbjegavamo nejednoznačnost reduciraj/reduciraj tako da produkcije (tj. stavke koji
         * sadrže produkcije) sortiramo prema rednom broju unutar gramatike te izaberemo najmanji
         */
        Optional<Item> item = items.stream().filter((it -> it.dotAtEnd() && terminal.equals(it.getTerminal()) &&
                                                           !it.getProduction().left.equals(Grammar.augmentedStart)))
                .sorted((it1, ite2) -> Integer
                        .compare(grammar.indexOf(it1.getProduction()), grammar.indexOf(ite2.getProduction())))
                .findFirst();

        if (item.isPresent()) {
            actions.putIfAbsent(new Pair(position, terminal),
                    new Action(Action.ActionEnum.REDUCE, grammar.indexOf(item.get().getProduction())));

            return true;
        }

        return false;
    }

    private static List<String> readLines() {
        Scanner sc = new Scanner(System.in);
        List<String> lines = new ArrayList<>();

        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }

        return lines;
    }

    private static void computeFirst() {
        grammar.getSymbols().forEach(s -> first.put(s, new HashSet<>()));

        // ponavljamo petlju dok nismo u mogućnosti dodati više niti jedan znak u neki od skupova ZapočinjeZnakom
        boolean added;
        do {
            added = false;
            for (Symbol symbol : grammar.getSymbols()) {
                boolean tempAdded;
                if (symbol instanceof Symbol.Terminal) {
                    //u slučaju da je terminal Započinje(a) = {a}
                    tempAdded = first.get(symbol).add(symbol);
                } else {
                    tempAdded = firstForNonterminal((Symbol.Nonterminal) symbol);
                }

                if (!added && tempAdded) {
                    added = true;
                }
            }
        } while (added);
    }

    private static boolean firstForNonterminal(Symbol.Nonterminal symbol) {
        /**
         * Neka imamo nezavršni znak X i skup produkcija oblika X -> Y1Y2Y3...Yn. Za svaku produkciju radimo sljedeće:
         * 1. U slučaju da je a € Yi i epsilon € Y1, Y2, ..., Yj (j = i - 1) tada dodajemo nezavršni znak a u skup
         * ZapočinjeZnakom(X). U slučaju da epsilon se ne nalazi u Yi, nakon dodavanja znakova iz ZapočinjeZnakom(Yi)
         * završavamo s danom produkcijom.
         * 2. U slučaju da svaki znak produkcije sadrži epsilon prijelaz, tada dodajemo i epsilon u ZapočinjeZnakom(X)
         * 3. u slučaju produkcije X -> epsilon, također dodajemo epsilon u ZapočinjeZnakom(X)
         */
        boolean added = false;

        Set<Symbol> fX = first.get(symbol);

        Set<Production> productions = grammar.getProductions(symbol);
        for (Production production : productions) {
            if (production.isEpsilon()) {
                added = first.get(symbol).add(Symbol.Epsilon.getEpsilon());
                continue;
            }

            int size = production.size();
            for (int i = 0; i < size; i++) {
                Set<Symbol> fY = first.get(production.getSymbol(i));

                for (Symbol s : fY) {
                    if (s.equals(Symbol.Epsilon.getEpsilon())) {
                        continue;
                    }

                    boolean tempBoolean = fX.add(s);
                    if (!added && tempBoolean) {
                        added = true;
                    }
                }

                if (!fY.contains(Symbol.Epsilon.getEpsilon())) {
                    break;
                }

                if (i == size - 1 && !fX.contains(Symbol.Epsilon.getEpsilon())) {
                    added = fX.add(Symbol.Epsilon.getEpsilon());
                }
            }
        }

        return added;
    }

    private static Set<Symbol> getFirst(List<Symbol> symbols) {
        /**
         * Dohvaćamo skup ZapočinjeZnakom za polje znakova. Način je sličan kao i kod traženja skupa za nezavršni
         * znak stoga neću ponovno opisivati korake.
         */
        Set<Symbol> f = new HashSet<>();
        int size = symbols.size();

        for (int i = 0; i < size; i++) {
            Symbol s = symbols.get(i);
            Set<Symbol> firstForS = first.get(s);

            firstForS.forEach(symbol -> {
                if (!symbol.equals(Symbol.Epsilon.getEpsilon())) {
                    f.add(symbol);
                }
            });

            if (!firstForS.contains(Symbol.Epsilon.getEpsilon())) {
                break;
            }

            if (i == size - 1) {
                f.add(Symbol.Epsilon.getEpsilon());
            }
        }

        return f;
    }

    private static Set<Item> closure(Set<Item> items) {
        Set<Item> closure = new HashSet<>(items);

        //petlja se ponavlja dok je moguće dodati novu stavku u skup
        boolean added;
        do {
            added = false;
            //izdvajamo sve stavke oblika [X -> alpha ×A ß, a]
            Set<Item> temp = closure.stream().filter(Item::isBeforeNonterminal).collect(Collectors.toSet());
            for (Item item : temp) {
                boolean tempAdded;

                Symbol.Nonterminal nonTerminal = item.getNonterminal();

                //radi se polje znakova oblika (ßa)
                List<Symbol> after = item.getAfterNonterminal();
                after.add(item.getTerminal());

                //pronalaze se produkcije oblika A -> gamma
                Set<Production> productions = grammar.getProductions(nonTerminal);

                for (Production production : productions) {
                    //pronalazi se skup ZapočinjeZnakom(ßa)
                    Set<Symbol.Terminal> t = getFirst(after).stream().filter(s -> s instanceof Symbol.Terminal)
                            .map(symbol -> (Symbol.Terminal) symbol).collect(Collectors.toSet());
                    //za svaki završni znak gradi se stavka oblika [A -> ×gamma, a] i dodaje se u skup
                    for (Symbol.Terminal terminal : t) {
                        tempAdded = closure.add(new Item(production, 0, terminal));

                        if (!added && tempAdded) {
                            added = true;
                        }
                    }
                }
            }
        } while (added);

        return closure;
    }

    private static Set<Item> goTo(Set<Item> items, Symbol symbol) {
        /**
         *  Za svaki skup stavki I i znak X definira se GOTO(I,X) na sljedeći način:
         *  1. Pronađi sve stavke oblika [A -> alpha *X ß, a]
         *  2. U rezultatni skup R dodaj novi stavku oblika [A -> alpha X* ß, a]
         *  3. Za rezultatni skup pronađi CLOSURE(R)
         */
        Set<Item> result = new HashSet<>();
        items = items.stream().filter(i -> i.dotBefore(symbol)).collect(Collectors.toSet());
        for (Item item : items) {
            result.add(new Item(item.getProduction(), item.getDotPosition() + 1, item.getTerminal()));
        }

        return closure(result);
    }

    private static void canonical() {
        /**
         * Pronađi CLOSURE([S' -> *S, $]) (dolar oznaka za kraj niza) i dodaj ga u kanonski skup
         */
        Set<Item> closure =
                closure(Collections.singleton(new Item(grammar.getProduction(0), 0, Symbol.Terminal.END_MARKER)));
        canonical.add(closure);

        //dok možemo pronaći novi skup C
        boolean added;
        do {
            added = false;
            Set<Set<Item>> copy = new HashSet<>(canonical);
            //za svaki C € kanonskog skupa i svaki znak X pronađi GOTO(C, X)
            for (Set<Item> c : copy) {
                for (Symbol s : grammar.getSymbols()) {
                    Set<Item> goTo = goTo(c, s);
                    //u slučaju da R = GOTO(C, X) nije prazan i R se ne nalazi u kanonskom skupu dodaj ga
                    if (!goTo.isEmpty() && !canonical.contains(goTo)) {
                        canonical.add(goTo);
                        added = true;
                    }
                }
            }
        } while (added);
    }
}
