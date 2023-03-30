import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class View {
    public static void main(String[] args) throws FileNotFoundException {
        boolean open = true;
        while (open){
            System.out.println("Ввести уравнение: 1");
            System.out.println("Решить уравнение: 2");
            System.out.println("Выбрать уравнение для сохранения: 3");
            System.out.println("Для того чтобы получить сохраненные уравнения из файла: 4");
            System.out.println("Выбрать для решения считанное уравнение: 5");
            System.out.println("Выход: 6");
            Scanner scaner = new Scanner(System.in);
            int num = 0;
            try {
                num = scaner.nextInt();
            }
            catch (RuntimeException e){
                System.out.println("Надо ввести число!");
            }

            if(num==6){
                open = false;
            }

            else  if(num==1){
                System.out.println("Введите уравнение: ");
                Scanner scanner = new Scanner(System.in);
                String vr = scanner.nextLine();
                Controller controller = new Controller(vr);
            }

            else if(num==2&&Controller.ctF()){
                String result = Controller.sc();
                System.out.println("Решение: " + result);
            }

            else if(num==2){
                System.out.println("Не введено уравнение!");
            }

            else if(num==3){
                ArrayList<String> arr;
                try {
                    arr = Controller.getArrayList();
                    int i = 1;
                    for (String st: arr){
                        System.out.println(i++ + "  " + st);
                    }
                    System.out.println("Введите путь файла: ");
                    Scanner scanner2 = new Scanner(System.in);
                    String nameFile = scanner2.nextLine();

                    File file = new File(nameFile);
                    PrintWriter printWriter = new PrintWriter(new FileWriter(file, true));

                    boolean trFal = true;
                    while (trFal){
                        System.out.println("Выберите номер сохраняемого уравнения из перечисленных выше, " +
                                "Для выхода введите 0.");
                        Scanner scanner3 = new Scanner(System.in);
                        try {
                            int j = scanner3.nextInt();
                            if(j==0){
                                break;
                            }
                            printWriter.println(arr.get(j-1));
                        }catch (RuntimeException e){
                            System.out.println("Введите корректное число");
                            continue;
                        }
                    }
                    printWriter.close();
                }
                catch (RuntimeException e){
                    System.out.println("Вы ещё не ввели за этот сеонс ни одного уравнения!");
                }catch (FileNotFoundException e) {
                    System.out.println("Не удолось получить доступ к файлу. Проверьте корректность введенного пути.");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (num==4){
                ArrayList<String> arrayList = new ArrayList<>();
                System.out.println("Введите путь файла: ");
                Scanner scanner = new Scanner(System.in);
                String fileName = scanner.nextLine();

                File file = new File(fileName);

                BufferedReader reader = new BufferedReader(new FileReader(file));
                reader.lines().forEach((e)-> arrayList.add(e));
                Controller.setStringArrayList(arrayList);
            }

            else if (num==5){
                int i = 1;
                for (String st: Controller.getStringArrayList()){
                    System.out.println(i++ + " " + st);
                }
                while (true){
                    System.out.println("Выберите уравнение для решения. Для выхода введите 0: ");
                    Scanner scanner = new Scanner(System.in);
                    try {
                        int id = scanner.nextInt();
                        if (id==0){
                            break;
                        }
                        Controller controller =  new Controller(Controller.getStringArrayList().get(id-1));
                        System.out.println("Решение: " + Controller.sc());
                    }catch (RuntimeException e){
                        System.out.println("Введите корректное значение.");
                    }
                }
            }
            else continue;
        }
    }
}