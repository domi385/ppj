package ga;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class describes code environment (scope).
 */
public class Environment {

    /**
     * Reference to parent environment.
     */
    private Environment parentEnvironment;

    private Map<String, TableEntry> parameterTable = new LinkedHashMap<>();

    /**
     * Identificator table.
     */
    private Map<String, TableEntry> localTable;

    /**
     * Function identificator table
     */
    private Map<String, FunctionTableEntry> functionsTable;

    private List<Environment> childrenEnvironments;

    public Environment(Environment parentEnvironment) {
        super();
        this.parentEnvironment = parentEnvironment;
        localTable = new LinkedHashMap<>();
        functionsTable = new HashMap<>();
        childrenEnvironments = new ArrayList<>();
    }

    public Map<String, TableEntry> getLocalTable() {
        return localTable;
    }

    public Environment getParentEnvironment() {
        return parentEnvironment;
    }

    public void declareParameter(String name, int size) {
        parameterTable.put(name, new TableEntry(name, size));
    }

    public int numberOfLocalVariables() {
        return localTable.size();
    }

    public int numberOfParameters() {
        return parameterTable.size();
    }

    public Map<String, FunctionTableEntry> getFunctionsTable() {
        return functionsTable;
    }

    public List<Environment> getChildrenEnvironments() {
        return childrenEnvironments;
    }

    public void addChildrenEvironment(Environment childEnvironment) {
        childrenEnvironments.add(childEnvironment);
    }

    public static Environment getGlobalEnvironment(Environment environment) {
        Environment currEnvironment = environment;
        while (currEnvironment.parentEnvironment != null) {
            currEnvironment = currEnvironment.parentEnvironment;
        }
        return currEnvironment;
    }

    public static boolean checkFunctionDeclaration(
            Environment environment, String name, Types returnType, List<Types> parametersType) {

        if (!environment.functionsTable.containsKey(name)) {
            return false;
        }

        FunctionTableEntry currFunction = new FunctionTableEntry(name, returnType, parametersType, false);

        return environment.functionsTable.get(name).equals(currFunction);
    }

    public boolean isFunctionDeclared(String name, Types returnType, List<Types> parametersType) {
        Environment currEnvironment = this;
        while (currEnvironment != null) {
            if (checkFunctionDeclaration(currEnvironment, name, returnType, parametersType)) {
                return true;
            }
            currEnvironment = currEnvironment.parentEnvironment;
        }
        return false;
    }

    public void declareFunction(String name, Types returnType, List<Types> parametersType) {
        FunctionTableEntry function = new FunctionTableEntry(name, returnType, parametersType, false);
        if (!functionsTable.containsKey(name)) {
            functionsTable.put(name, function);
        }

    }

    public TableEntry declareLocalVariable(String name, int size) {
        TableEntry entry = new TableEntry(name, size);
        localTable.put(name, entry);
        return entry;
    }

    public void defineFunction(String name, Types returnType, List<Types> parametersType) {
        functionsTable.put(name, new FunctionTableEntry(name, returnType, parametersType, true));
    }

    public boolean isDeclared(String name) {
        Environment currentEnvironment = this;
        while (currentEnvironment != null) {
            if (currentEnvironment.isDeclaredLocaly(name)) {
                return true;
            }
            currentEnvironment = currentEnvironment.parentEnvironment;
        }
        return false;
    }

    public boolean isDeclaredLocaly(String name) {
        return localTable.containsKey(name) || functionsTable.containsKey(name);
    }

    public boolean isDefinedFunction(String name) {
        if (!functionsTable.containsKey(name)) {
            return false;
        }
        return functionsTable.get(name).defined;
    }

    public static class FunctionTableEntry {
        String name;
        Types returnType;
        List<Types> parametersType;
        Boolean defined;

        public FunctionTableEntry(
                String name, Types returnType, List<Types> parametersType, Boolean defined) {
            super();
            this.name = name;
            this.returnType = returnType;
            this.parametersType = parametersType;
            this.defined = defined;
        }

        public Boolean getDefined() {
            return defined;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {

                return true;
            }
            if (obj == null) {

                return false;
            }
            if (getClass() != obj.getClass()) {

                return false;
            }
            FunctionTableEntry other = (FunctionTableEntry) obj;
            if (name == null) {
                if (other.name != null) {

                    return false;
                }
            } else if (!name.equals(other.name)) {

                return false;
            }
            if (parametersType == null) {
                if (other.parametersType != null) {

                    return false;
                }
            } else if (!parametersType.equals(other.parametersType)) {

                return false;
            }
            if (returnType == null) {
                if (other.returnType != null) {

                    return false;
                }
            } else if (!returnType.equals(other.returnType)) {

                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result + ((parametersType == null) ? 0 : parametersType.hashCode());
            result = prime * result + ((returnType == null) ? 0 : returnType.hashCode());
            return result;
        }

    }

    public enum DeclarationType {
        PARAM, LOCAL
    }

    public static class TableEntry {
        String name;
        Object[] definedValue;
        int size;
        String pointer;

        public TableEntry(String name, int size) {
            super();
            this.name = name;
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public Object[] getDefinedValue() {
            return definedValue;
        }

        public String getPointer() {
            return pointer;
        }

        public int getSize() {
            return size;
        }

        public void setPointer(String pointer) {
            this.pointer = pointer;
        }
    }



    public Types getFunctionReturnType(String functionName) {
        Environment currEnvironment = this;
        while (currEnvironment != null) {
            if (currEnvironment.functionsTable.containsKey(functionName)) {
                return currEnvironment.functionsTable.get(functionName).returnType;
            }
            currEnvironment = currEnvironment.parentEnvironment;
        }
        return null;
    }

    public List<Types> getFunctionParameters(String functionName) {
        Environment currEnvironment = this;
        while (currEnvironment != null) {
            if (currEnvironment.functionsTable.containsKey(functionName)) {
                return currEnvironment.functionsTable.get(functionName).parametersType;
            }
            currEnvironment = currEnvironment.parentEnvironment;
        }
        return null;
    }

}
