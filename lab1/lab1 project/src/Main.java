import java.util.Random;

// Поток для генерации случайных слов или предложений
class SentenceGenerator extends Thread {

    @Override
    public void run() {
        String[] words = {"Java", "is", "fun", "to", "learn", "code", "program", "easy", "powerful"};
        Random random = new Random();

        // Генерация 10 предложений
        for (int iteration = 0; iteration < 10; iteration++) {
            StringBuilder generatedSentence = new StringBuilder();

            // Генерация случайного предложения из случайных слов
            int wordCount = random.nextInt(5) + 3; // Предложение будет от 3 до 7 слов
            for (int i = 0; i < wordCount; i++) {
                generatedSentence.append(words[random.nextInt(words.length)]).append(" ");
            }
            String sentence = generatedSentence.toString().trim() + ".";
            System.out.println("Generated Sentence: \n" + sentence);

            // Проверка сгенерированного предложения
            SentenceChecker checker = new SentenceChecker(sentence);
            checker.start();
            try {
                checker.join(); // Ждем завершения проверки перед генерацией следующего предложения
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

// Поток для проверки грамматических или синтаксических ошибок
class SentenceChecker extends Thread {
    private final String sentence;

    public SentenceChecker(String sentence) {
        this.sentence = sentence;
    }

    @Override
    public void run() {
        // Простая проверка: предложение должно начинаться с заглавной буквы и заканчиваться точкой

        boolean startsWithCapital = Character.isUpperCase(sentence.charAt(0));
        boolean endsWithPeriod = sentence.endsWith(".");

        if (startsWithCapital && endsWithPeriod) {
            System.out.println("☑️ The sentence is grammatically correct. \n-------------");
        } else {
            System.out.println("\uD83D\uDDD9 The sentence has grammatical errors. \n-------------");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Создаем и запускаем поток для генерации предложений и их проверки
        SentenceGenerator generator = new SentenceGenerator();
        generator.start();

        try {
            generator.join(); // Ждем завершения всех 10 итераций
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
