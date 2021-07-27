import frontend.CFrontEnd;

public class LexicalAnalysis {
    public static void main(String ...args) {
        CFrontEnd frontEnd = new CFrontEnd();
        frontEnd.convertFunctionsOccurrencesToToken("function void integer(){} \n" +
                "function int removeNode(node *node){" +
                "\n" +
                "int c = 0;}");
    }
}
