import consoleHelper.ConsoleHelper;
import exception.MyInterruptedException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class CaesarCipher {
    private static String alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static String punctuationSymbols = ".,!?:;-\r\n\t ";

    public static void main(String[] args) {
        CaesarCipher caesarCipher = new CaesarCipher();
        caesarCipher.chooseAction();
    }

    public void chooseAction() {
        while (true) {
            ConsoleHelper.writeMessage("<<< Пожалуйста, выберите, что вы хотите сделать:\r\n\r\n");
            ConsoleHelper.writeMessage("1 - Расшифровать содержимое файла\r\n2 - Зашифровать содержимое файла\r\n");
            try {
                int chosenNumber = ConsoleHelper.readInt();
                if (chosenNumber == 1) {
                    decryptContentAction();
                    break;
                } else if (chosenNumber == 2) {
                    encryptContentAction();
                    break;
                }
                ConsoleHelper.writeMessage("Вы выбрали неверный номер. Повторите попытку.\r\n");
            } catch (MyInterruptedException e) {
                ConsoleHelper.writeMessage("<<< До свидания!");
                break;
            } catch (IOException | NumberFormatException e) {
                ConsoleHelper.writeMessage("Вы ввели некорректные данные. Повторите попытку.\r\n");
            }
        }
    }

    private void decryptContentAction() throws MyInterruptedException {
        String sourcePath = "";
        String destinationPath = "";
        int shift;
        while (true) {
            try {
                if (Files.isRegularFile(Paths.get(sourcePath))) {
                    while (true) {
                        ConsoleHelper.writeMessage("<<< Выберите действие:\r\n\r\n");
                        ConsoleHelper.writeMessage("1 - Попытаться расшифровать содержимое файла самостоятельно в консоли\r\n" +
                                "2 - Доверить перебор всех вариантов программе\r\n");
                        try {
                            int chosenNumber = ConsoleHelper.readInt();
                            if (chosenNumber == 1) {
                                while (true) {
                                    try {
                                        ConsoleHelper.writeMessage("Пожалуйста, введите количество символов, на которое хотите выполнить сдвиг вперед: ");
                                        shift = ConsoleHelper.readInt();
                                        if (shift <= 0) {
                                            ConsoleHelper.writeMessage("Число не может быть меньше или равно 0. Пожалуйста, повторите попытку\r\n");
                                            continue;
                                        } else if (shift > alphabet.length()) {
                                            ConsoleHelper.writeMessage("Число не может превышать количество букв алфавита. Пожалуйста, повторите попытку\r\n");
                                            continue;
                                        }
                                        decryptContentToConsole(sourcePath, shift);
                                        ConsoleHelper.writeMessage("\r\n\r\nВас устроил результат дешифрования? <y/n>");
                                        String answer = ConsoleHelper.readString();
                                        if (answer.equalsIgnoreCase("y")) {
                                            break;
                                        }
                                    } catch (IOException | NumberFormatException e) {
                                        ConsoleHelper.writeMessage("Некорректные данные. Повторите попытку\r\n");
                                    }
                                }
                                break;
                            } else if (chosenNumber == 2) {
                                while (true) {
                                    ConsoleHelper.writeMessage("Введите путь к файлу, в который хотите записать все попытки расшифровки:\r\n");
                                    destinationPath = ConsoleHelper.readString();
                                    if (Files.isRegularFile(Paths.get(destinationPath))) {
                                        break;
                                    } else {
                                        try {
                                            Files.createFile(Paths.get(destinationPath));
                                        } catch (InvalidPathException e) {
                                            ConsoleHelper.writeMessage("Неверный путь. Повторите попытку.\r\n");
                                        }
                                    }
                                }
                                decryptContentToFile(sourcePath, destinationPath);
                                break;
                            }
                            ConsoleHelper.writeMessage("Вы выбрали неверный номер. Повторите попытку.\r\n");
                        } catch (IOException | NumberFormatException e) {
                            ConsoleHelper.writeMessage("Вы ввели некорректные данные. Повторите попытку.\r\n");
                        }
                    }
                    break;

                } else {
                    ConsoleHelper.writeMessage("<<< Пожалуйста, укажите полный путь к файлу, содержимое которого хотите расшифровать:\r\n");
                    sourcePath = ConsoleHelper.readString();
                    if (!Files.isRegularFile(Paths.get(sourcePath))) {
                        ConsoleHelper.writeMessage("Некорректный путь. Повторите попытку.\r\n");
                    }
                }
            } catch (IOException e) {
                ConsoleHelper.writeMessage("Вы ввели некорректные данные. Повторите попытку.\r\n");
            }
        }
    }

    private void encryptContentAction() throws MyInterruptedException {
        String sourcePath = "";
        int shift;
        while (true) {
            try {
                if (sourcePath.isEmpty() || !Files.isRegularFile(Paths.get(sourcePath))) {
                    ConsoleHelper.writeMessage("<<< Пожалуйста, укажите полный путь к файлу, содержимое которого хотите зашифровать:\r\n");
                    sourcePath = ConsoleHelper.readString();
                } else {
                    while (true) {
                        try {
                            ConsoleHelper.writeMessage("Пожалуйста, введите количество символов, на которое хотите выполнить сдвиг назад для шифрования: ");
                            shift = ConsoleHelper.readInt();
                            if (shift <= 0) {
                                ConsoleHelper.writeMessage("Число не может быть меньше или равно 0. Пожалуйста, повторите попытку\r\n");
                                continue;
                            } else if (shift > alphabet.length()) {
                                ConsoleHelper.writeMessage("Число не может превышать количество букв в алфавите. Пожалуйста, повторите попытку\r\n");
                                continue;
                            }
                            encryptContentToConsole(sourcePath, shift);
                            ConsoleHelper.writeMessage("\r\n\r\nВас устроил результат шифрования? <y/n>");
                            String answer = ConsoleHelper.readString();
                            if (answer.equalsIgnoreCase("y")) {
                                break;
                            }
                        } catch (IOException | NumberFormatException e) {
                            ConsoleHelper.writeMessage("Вы ввели некорректные данные. Повторите попытку\r\n");
                        }
                    }
                    break;
                }
            } catch (IOException e) {
                ConsoleHelper.writeMessage("Вы ввели некорректные данные. Повторите ввод.\r\n");
            }

        }
    }

    private void decryptContentToFile(String source, String destination) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(destination);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"))) {
            String contentForDecrypt = readFile(source);
            for (int i = 1; i <= alphabet.length(); i++) {
                writer.write("Попытка расшифровать текст при сдвиге на " + i + " символов вперед:\r\n");
                for (int j = 0; j < contentForDecrypt.length(); j++) {
                    char letter = contentForDecrypt.charAt(j);
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

    private void decryptContentToConsole(String source, int shift) {
        String contentForDecrypt = readFile(source);
        StringBuilder builder = new StringBuilder();
        ConsoleHelper.writeMessage("\r\nПопытка расшифровать текст при сдвиге на " + shift + " символов вперед:\r\n\r\n");
        for (int j = 0; j < contentForDecrypt.length(); j++) {
            char letter = contentForDecrypt.charAt(j);
            if (!punctuationSymbols.contains(Character.toString(letter))) {
                int newLetterIndex = alphabet.indexOf(Character.toLowerCase(letter)) + shift;
                if (newLetterIndex >= alphabet.length()) {
                    newLetterIndex -= alphabet.length();
                }
                letter = (Character.isLowerCase(letter) ? alphabet.charAt(newLetterIndex) : Character.toUpperCase(alphabet.charAt(newLetterIndex)));
            }
            builder.append(letter);
        }
        ConsoleHelper.writeMessage(builder.toString());
    }

    private void encryptContentToConsole(String source, int shift) {
        String contentForEncrypt = readFile(source);
        StringBuilder builder = new StringBuilder();
        ConsoleHelper.writeMessage("\r\nЗашифрованный текст при сдвиге на " + shift + " символов назад:\r\n\r\n");
        for (int j = 0; j < contentForEncrypt.length(); j++) {
            char letter = contentForEncrypt.charAt(j);
            if (!punctuationSymbols.contains(Character.toString(letter))) {
                int newLetterIndex = alphabet.indexOf(Character.toLowerCase(letter)) - shift;
                if (newLetterIndex < 0) {
                    newLetterIndex += alphabet.length();
                }
                letter = (Character.isLowerCase(letter) ? alphabet.charAt(newLetterIndex) : Character.toUpperCase(alphabet.charAt(newLetterIndex)));
            }
            builder.append(letter);
        }
        ConsoleHelper.writeMessage(builder.toString());
    }

    private String readFile(String filePath) {
        File file = new File(filePath);
        StringBuilder builder = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"))) {
            int k;
            while ((k = reader.read()) != -1) {
                builder.append((char) k);
            }
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Ошибка при чтении файла\r\n");
            e.printStackTrace();
        }
        return builder.toString();
    }
}
