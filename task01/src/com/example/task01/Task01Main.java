package com.example.task01;

import java.io.*;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(extractSoundName(new File("task01/src/main/resources/3727.mp3")));
    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        String ffprobePath = "/Users/dreeameer/Downloads/ffm/ffprobe";

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder
                .command(ffprobePath, "-v", "error", "-of", "flat", "-show_format", file.getAbsolutePath())
                .redirectErrorStream(true);

        Process process = processBuilder.start();
        InputStream inputStream = process.getInputStream();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String str = bufferedReader.readLine();
            while (str != null) {
                if (str.contains("title")) {
                    return str.split("=")[1].replace("\"", "");
                }
                str = bufferedReader.readLine();
            }
        }

        return null;
    }
}