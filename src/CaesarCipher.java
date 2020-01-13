import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CaesarCipher {
    public static void main(String[] args) {
        CaesarCipher caesarCipher = new CaesarCipher();
        caesarCipher.decryptContent("sampleForDecrypt.txt", "decryptContent.txt");
        caesarCipher.encryptContent("sampleForEncrypt.txt", "encryptContent.txt");
    }

    public void decryptContent(String source, String destination) {
        String alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        String punctuationSymbols = ".,!?:;-\r\n\t ";
        String encryptedContent = readFile(source);
        try (FileOutputStream fileOutputStream = new FileOutputStream(Paths.get(this.getClass().getPackageName()).toAbsolutePath() + "/src/" + destination)) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            for (int i = 1; i <= alphabet.length(); i++) {
                writer.write("Попытка расшифровать текст при сдвиге на " + i + " символов вперед:\r\n\r\n");
                for (int j = 0; j < encryptedContent.length(); j++) {
                    char letter = encryptedContent.charAt(j);
                    if (!punctuationSymbols.contains(Character.toString(letter))) {
                        int newLetterIndex = alphabet.indexOf(Character.toLowerCase(letter)) + i;
                        if (newLetterIndex >= alphabet.length()) {
                            newLetterIndex -= alphabet.length();
                        }
                        letter = (Character.isLowerCase(letter) ? alphabet.charAt(newLetterIndex) : Character.toUpperCase(alphabet.charAt(newLetterIndex)));
                    }
                    writer.write(letter);
                    writer.flush();
                }
                writer.write("\r\n------------------------------------------\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void encryptContent(String source, String destination) {
        String alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        String punctuationSymbols = ".,!?:;-\r\n\t ";
        String contentForEncrypt = readFile(source);
        try (FileOutputStream fileOutputStream = new FileOutputStream(Paths.get(this.getClass().getPackageName()).toAbsolutePath() + "/src/" + destination)) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            for (int i = 1; i <= alphabet.length(); i++) {
                writer.write("Зашифрованный текст при сдвиге на " + i + " символов назад:\r\n\r\n");
                for (int j = 0; j < contentForEncrypt.length(); j++) {
                    char letter = contentForEncrypt.charAt(j);
                    if (!punctuationSymbols.contains(Character.toString(letter))) {
                        int newLetterIndex = alphabet.indexOf(Character.toLowerCase(letter)) - i;
                        if (newLetterIndex < 0) {
                            newLetterIndex += alphabet.length();
                        }
                        letter = (Character.isLowerCase(letter) ? alphabet.charAt(newLetterIndex) : Character.toUpperCase(alphabet.charAt(newLetterIndex)));
                    }
                    writer.write(letter);
                    writer.flush();
                }
                writer.write("\r\n------------------------------------------\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFile(String filePath) {
        URL resource = this.getClass().getClassLoader().getResource(filePath);
        Path path = null;
        try {
            path = Paths.get(resource.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try (FileInputStream fileInputStream = new FileInputStream(path.toFile())) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            int k;
            StringBuilder builder = new StringBuilder();
            while ((k = reader.read()) != -1) {
                builder.append((char) k);
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
