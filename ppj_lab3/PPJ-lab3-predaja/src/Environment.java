

import java.util.ArrayList;
import java.util.HashMap;
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

    /**
     * Identificator table.
     */
    private Map<String, TableEntry> identificatorTable;

    /**
     * Function identificator table
     */
    private Map<String, FunctionTableEntry> functionsTable;

    private List<Environment> childrenEnvironments;

    public Environment(Environment parentEnvironment) {
        super();
        this.parentEnvironment = parentEnvironment;
        identificatorTable = new HashMap<>();
        functionsTable = new HashMap<>();
        childrenEnvironments = new ArrayList<Environment>();
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

    public static boolean checkFunctionDeclaration(Environment environment, String name,
            Types returnType, List<Types> parametersType) {
    	
        if (!environment.functionsTable.containsKey(name)) {        	
            return false;
        }
        
        FunctionTableEntry currFunction = new FunctionTableEntry(name, returnType, parametersType,
                false);
        
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
        FunctionTableEntry function = new FunctionTableEntry(name, returnType, parametersType,
                false);
        if (!functionsTable.containsKey(name)) {
            functionsTable.put(name, function);
        }

    }

    public void declareIdentificator(String name, Types type) {
        identificatorTable.put(name, new TableEntry(name, type));
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
        return identificatorTable.containsKey(name) || functionsTable.containsKey(name);
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

        public FunctionTableEntry(String name, Types returnType, List<Types> parametersType,
                Boolean defined) {
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

    public static class TableEntry {
        String name;
        Types type;

        public TableEntry(String name, Types type) {
            super();
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public Types getType() {
            return type;
        }

    }

    public Types getIdentificatorType(String identificatorName) {
        Environment currEnvironment = this;
        while (currEnvironment != null) {
            if (currEnvironment.functionsTable.containsKey(identificatorName)) {
                return Types.FUNCTION;
            } else if (currEnvironment.identificatorTable.containsKey(identificatorName)) {
                return currEnvironment.identificatorTable.get(identificatorName).getType();
            }
            currEnvironment = currEnvironment.parentEnvironment;
        }
        return null;
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

    public static Types getDeclaredType(String name, Environment environment) {
        Environment currentEnvironment = environment;
        if (currentEnvironment.isDeclaredLocaly(name)) {
            return currentEnvironment.getIdentificatorType(name);
        }

        currentEnvironment = currentEnvironment.parentEnvironment;

        while (currentEnvironment != null) {
            if (currentEnvironment.isDeclaredLocaly(name)) {
                return currentEnvironment.getIdentificatorType(name);
            }
            currentEnvironment = currentEnvironment.parentEnvironment;
        }
        return null;

    }
}
