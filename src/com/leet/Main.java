package com.leet;

        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileFilter;
        import java.io.FileReader;
        import java.io.IOException;
        import java.io.PrintWriter;
        import java.util.List;
        import java.util.Objects;

public class Main {

    int c = 0;

    public static void main(String[] args) throws IOException {

        Main main = new Main();
        main.go();


    }

    void go() throws IOException {
        File file = new File("/Users/ben/xfinity/code/XFHome_Android");


        for (File f : file.listFiles()) {
            // System.out.println(f.getAbsolutePath());
            File c = new File(f.getAbsolutePath());
            if (c.isDirectory()) {
                listFiles(c);
            }
            //listFiles(s);
        }
        System.out.println(c);
    }


    void listFiles(File p) throws IOException {



        for (File f : p.listFiles()) {
            //  System.out.println(f.getAbsolutePath());
            if (f.isDirectory() && f.listFiles() != null) {
                listFiles(f);
            }
            else if (f.getName().endsWith(".java") || f.getName().endsWith(".kt")) {

                processFile(f);
            }


        }



    }

    void processFile(File f) throws IOException {
        //  System.out.println(f.getAbsolutePath());
        StringBuilder stringBuilder = new StringBuilder();
        boolean changed = false;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("ButterKnife.findById(")) {

                    c++;
                    int i = line.indexOf("ButterKnife.findById(");
                    int x = line.indexOf(", ", i);
                    String seg = line.substring(i, x);
                    System.out.println(line);
                    String view = seg.substring(seg.indexOf("(") + 1);

                    String fixed = line.replace("ButterKnife.findById(" + view + ",", view + ".findViewById(");
                    stringBuilder.append(fixed + "\n");
                    changed = true;
//                       String view = seg.split("(")[1];
                    // System.out.println(view);
                }

                else {
                    stringBuilder.append(line + "\n");
                }
            }
        }

        if (changed) {

            PrintWriter out = new PrintWriter(f.getAbsolutePath());
            out.write(stringBuilder.toString() + "\n");
            out.close();
        }
    }

}
