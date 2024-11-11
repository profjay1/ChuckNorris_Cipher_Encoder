package chucknorris;

import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        boolean running = true;
        while (running) {
//            try {
                System.out.print("Please input operation (encode/decode/exit): ");
                String input = sc.nextLine();
                Set<String> validOperations = Set.of("encode", "decode", "exit");
                if (!validOperations.contains(input)) {
                    System.out.println("There is no '" + input + "' operation");
                    continue;
                }

                switch (input) {
                    case "encode" -> {
                        System.out.println("Input string:");
                        String encodedString = chuckNorrisEncoding(sc.nextLine());
                        System.out.println("Encoded string:");
                        System.out.println(encodedString);
                    }
                    case "decode" -> {
                        System.out.println("Input encoded string:");
                        String decodedString = decodeFromChuckNorris(sc.nextLine());
                        if (decodedString != null) {
                            System.out.println("Decoded string:");
                            System.out.println(decodedString);
                        }
                    }
                    case "exit" -> {
                        System.out.println("Bye!");
                        running = false;
                    }
                }
//            } catch (Exception e) {
//                System.out.println("Operation unsuccessful");
//            }
        }

    }

    public static String chuckNorrisEncoding(String string) {

        StringBuilder binaryString = new StringBuilder();

        //Converting input to binary
        for (char ch : string.toCharArray()) {
            binaryString.append(String.format("%7s", Integer.toBinaryString(ch)).replace(' ', '0'));
        }

        //Encode the binary string with Chuck Norris encoding format
        StringBuilder encodedString = new StringBuilder();
        int i = 0;
        while (i < binaryString.length()) {
            char currentBit = binaryString.charAt(i);

            //Determine the prefix block for the current series
            encodedString.append(currentBit == '1' ? "0 " : "00 ");

            //Count the length of the consecutive series of the same bit
            int count = 0;
            while (i < binaryString.length() && binaryString.charAt(i) == currentBit) {
                count++;
                i++;
            }

                //Add a block with a number of '0's equal to the length of the series
                encodedString.append("0".repeat(count));

            //Add space if there are more bits to process
            if (i < binaryString.length()) {
                encodedString.append(" ");
            }
        }
        return encodedString.toString();
    }

    public static String decodeFromChuckNorris(String encodedString) {

        // Validate if the input contains only '0' and spaces
        if (!encodedString.matches("[ 0]+")) {
            System.out.println("not valid");
            return null;
        }

        // Split the input into blocks of zeros
        String[] blocks = encodedString.split(" ");
        if (blocks.length % 2 != 0) {
            System.out.println("not valid");
            return null;
        }


        StringBuilder binaryString = new StringBuilder();

        // Interpret each pair of blocks
        for (int i = 0; i < blocks.length; i += 2) {
            String blockType = blocks[i];
            String blockCount = blocks[i + 1];

            // Validate block format
            if (!blockType.equals("0") && !blockType.equals("00")) {
                System.out.println("not valid");
                return null;
            }
            if (!blockCount.matches("0+")) {
                System.out.println("not valid");
                return null;
            }

            // Decode based on block type
            binaryString.append(blockType.equals("0") ? "1".repeat(blockCount.length()) : "0".repeat(blockCount.length()));
        }

        // Check if binary string is a multiple of 7
        if (binaryString.length() % 7 != 0) {
            System.out.println("not valid");
            return null;
        }

        // Convert binary string to decoded characters
        StringBuilder decodedMessage = new StringBuilder();
        for (int i = 0; i < binaryString.length(); i += 7) {
            String sevenBitBinary = binaryString.substring(i, i + 7);
            int charCode = Integer.parseInt(sevenBitBinary, 2);
            decodedMessage.append((char) charCode);
        }
        return decodedMessage.toString();
    }
}
