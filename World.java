import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class World {
     private static JFrame frame;
     private JButton[][] board;
     private JPanel ArrowPanel = new JPanel(new GridLayout(3,3));
     public JTextArea TextArea = new JTextArea();
     private ListOfOrganisms organisms = new ListOfOrganisms();
     private int rounds;
     private boolean superpower;
     private int NextMove;
     private int width;
     private int height;

    World(){
         CreateBoard();
     }
    public void MakeRound(){
        TextArea.setText("");
        organisms.Move();
        ClearBoard();
        rounds += 1;
        organisms.DrawOrganism();
    }
    private void ClearBoard(){
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                board[i][j].setIcon(null);
            }
        }
    }
    public void CreateBoard2(){
        frame = new JFrame("Mikolaj Kabala 193380");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(new Dimension(1200,JFrame.MAXIMIZED_VERT));
        JPanel panel = new JPanel(new GridLayout(height+1,width+1));
        //JPanel panel = new JPanel(new GridLayout(21,21));
        //board = new JButton[20][20];
        board = new JButton[height][width];


        for(int i=0;i<=height;i++){
                for(int j=0;j<=width;j++){
                if((j==0)&&(i!=0)){
                    JLabel label = new JLabel(""+(i));
                    label.setBorder(new EmptyBorder(5,20,5,5));
                    panel.add(label);
                }
                else  if((i==0)&&(j!=0)){
                    JLabel label = new JLabel(""+(j));
                    label.setBorder(new EmptyBorder(5,20,5,5));
                    panel.add(label);
                }
                else if((i==0 ) && (j==0)){
                    JLabel label = new JLabel("X:Y");
                    label.setBorder(new EmptyBorder(5,15,5,5));
                    panel.add(label);
                }
                else{
                    JButton button = new JButton();
                    board[i - 1][j - 1] = button;

                    Border border = BorderFactory.createLineBorder(Color.BLACK,1);
                    button.setBorder(border);
                    button.setBackground(new Color(0x78DBBB));
                    button.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent a){
                            JButton addButton = new JButton("ADD ORGANISM");
                            Object[] options = {addButton,"BACK"};
                            addButton.addActionListener(new ActionListener(){
                                @Override
                                public void actionPerformed(ActionEvent a){
                                    AddOrganism(button);
                                }
                            });
                            if(button.getIcon()!=null){
                                ImageIcon icon = (ImageIcon) button.getIcon();
                                String icon_txt = icon.getDescription();

                                switch(icon_txt){
                                    case "src/Icons/antelope.png":
                                        JOptionPane.showOptionDialog(null,"ANTELOPE","FIELD",
                                                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                                                null,options,options[0]);
                                        break;
                                    case "src/Icons/bilberry.png":
                                        JOptionPane.showOptionDialog(null,"BILBERRY","FIELD",
                                                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                                                null,options,options[0]);
                                        break;
                                    case "src/Icons/dandelion.png":
                                        JOptionPane.showOptionDialog(null,"DANDELION","FIELD",
                                                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                                                null,options,options[0]);
                                        break;
                                    case "src/Icons/fox.png":
                                        JOptionPane.showOptionDialog(null,"FOX","FIELD",
                                                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                                                null,options,options[0]);
                                        break;
                                    case "src/Icons/grass.png":
                                        JOptionPane.showOptionDialog(null,"GRASS","FIELD",
                                                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                                                null,options,options[0]);
                                        break;
                                    case "src/Icons/guarana.png":
                                        JOptionPane.showOptionDialog(null,"GUARANA","FIELD",
                                                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                                                null,options,options[0]);
                                        break;
                                    case "src/Icons/posion.png":
                                        JOptionPane.showOptionDialog(null,"HOGWEED","FIELD",
                                                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                                                null,options,options[0]);
                                        break;
                                    case "src/Icons/sheep.png":
                                        JOptionPane.showOptionDialog(null,"SHEEP","FIELD",
                                                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                                                null,options,options[0]);
                                        break;
                                    case "src/Icons/turtle.png":
                                        JOptionPane.showOptionDialog(null,"TURTLE","FIELD",
                                                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                                                null,options,options[0]);
                                        break;
                                    case "src/Icons/wolf.png":
                                        JOptionPane.showOptionDialog(null,"WOLF","FIELD",
                                                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                                                null,options,options[0]);
                                        break;
                                    case "src/Icons/human.png":
                                        JOptionPane.showOptionDialog(null,"HUMAN","FIELD",
                                                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                                                null,options,options[0]);
                                        break;
                                    case "src/Icons/cyber.png":
                                        JOptionPane.showOptionDialog(null,"CYBER-SHEEP","FIELD",
                                                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                                                null,options,options[0]);
                                        break;
                                }

                            }
                            else{
                                JOptionPane.showOptionDialog(null,"THERE IS NO ORGANISM HERE, " +
                                                "BUT YOU CAN ADD ONE","FIELD",
                                        JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                                        null,options,options[0]);
                            }
                        }
                    });
                    panel.add(button);
                }
            }
        }
        organisms.Start(this);
        //TWORZENIE LEGENDY
        JLabel legendLabel = new JLabel("");
        legendLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new BorderLayout());
        legendPanel.add(legendLabel,BorderLayout.NORTH);
        legendPanel.add(TextArea,BorderLayout.CENTER);
        TextArea.setBackground(new Color(0xCDD9E5));
        Font font = new Font("Arial",Font.BOLD, 14);
        TextArea.setFont(font);
        legendPanel.setBackground(new Color(0xCDD9E5));


        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBackground(new Color(0xEAF4FF));
        Arrows(infoPanel);

        //DZIELENIE EKRANU
        JSplitPane splitPaneRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT,legendPanel,infoPanel);
        splitPaneRight.setDividerLocation(400);
        splitPaneRight.setDividerSize(0);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,panel ,splitPaneRight);
        splitPane.setDividerLocation(1200);
        splitPane.setDividerSize(0);

        frame.getContentPane().add(splitPane);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();

        //ROBOCZE
        frame.setVisible(true);
    }
    public void CreateBoard(){
        frame = new JFrame("Mikolaj Kabala 193380");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SetSuperpower(false);
        SetRounds(11);
        JTextField widthTextField = new JTextField();

        JTextField heightTextField = new JTextField();
        JButton dimensionsButton= new JButton("LET'S GO");
        dimensionsButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                String text_x= widthTextField.getText();
                String text_y= heightTextField.getText();

                try{
                    SetHeight(Integer.parseInt(text_y));
                    SetWidth(Integer.parseInt(text_x));
                    frame.getContentPane().removeAll();
                    frame.dispose();
                    CreateBoard2();

                }   catch(NumberFormatException ex){
                    System.out.println("Something went wrong with numbers");
                }
            }
        });
        JPanel textPanel = new JPanel(new GridLayout(3,1));
        textPanel.setPreferredSize(new Dimension(300,100));

        textPanel.add(new JLabel("WIDTH: "));
        textPanel.add(widthTextField);
        textPanel.add(new JLabel("HEIGHT: "));
        textPanel.add(heightTextField);
        textPanel.add(dimensionsButton);

        frame.getContentPane().add(textPanel);
        frame.pack();
        frame.setVisible(true);
    }
    private void AddOrganism(JButton button){
        Polozenie cords = GetButton(button);

        //ADDING BUTTONS TO CHOOSE ORGANISM
        JButton antelopeButton = new JButton("ANTELOPE");
        antelopeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                Antelope organism = new Antelope(organisms.GetHead().world, cords.x, cords.y);
                organisms.add(organism);
                organisms.Collision(organism);
                ClearBoard();
                organisms.DrawOrganism();
                Window window = JOptionPane.getRootFrame();
                window.dispose();
            }
        });
        JButton bilberryButton = new JButton("BILBERRY");
        bilberryButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                Bilberry bilberry = new Bilberry(organisms.GetHead().world, cords.x, cords.y);
                organisms.add(bilberry);
                organisms.Collision(bilberry);
                ClearBoard();
                organisms.DrawOrganism();
                Window window = JOptionPane.getRootFrame();
                window.dispose();
            }
        });
        JButton cyberSheepButton = new JButton("CYBER-SHEEP");
        cyberSheepButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                CyberSheep organism = new CyberSheep(organisms.GetHead().world, cords.x, cords.y);
                organisms.add(organism);
                organisms.Collision(organism);
                ClearBoard();
                organisms.DrawOrganism();
                Window window = JOptionPane.getRootFrame();
                window.dispose();
            }
        });
        JButton dandelionSheepButton = new JButton("DANDELION");
        dandelionSheepButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                Dandelion organism = new Dandelion(organisms.GetHead().world, cords.x, cords.y);
                organisms.add(organism);
                organisms.Collision(organism);
                ClearBoard();
                organisms.DrawOrganism();
                Window window = JOptionPane.getRootFrame();
                window.dispose();
            }
        });
        JButton foxButton = new JButton("FOX");
        foxButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                Fox organism = new Fox(organisms.GetHead().world, cords.x, cords.y);
                organisms.add(organism);
                organisms.Collision(organism);
                ClearBoard();
                organisms.DrawOrganism();
                Window window = JOptionPane.getRootFrame();
                window.dispose();
            }
        });
        JButton grassButton = new JButton("GRASS");
        grassButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                Grass organism = new Grass(organisms.GetHead().world, cords.x, cords.y);
                organisms.add(organism);
                organisms.Collision(organism);
                ClearBoard();
                organisms.DrawOrganism();
                Window window = JOptionPane.getRootFrame();
                window.dispose();
            }
        });
        JButton guaranaButton = new JButton("GUARANA");
        guaranaButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                Guarana organism = new Guarana(organisms.GetHead().world, cords.x, cords.y);
                organisms.add(organism);
                organisms.Collision(organism);
                ClearBoard();
                organisms.DrawOrganism();
                Window window = JOptionPane.getRootFrame();
                window.dispose();
            }
        });
        JButton hogweedButton = new JButton("HOGWEED");
        hogweedButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                Hogweed organism = new Hogweed(organisms.GetHead().world, cords.x, cords.y);
                organisms.add(organism);
                organisms.Collision(organism);
                ClearBoard();
                organisms.DrawOrganism();
                Window window = JOptionPane.getRootFrame();
                window.dispose();
            }
        });
        JButton sheepButton = new JButton("SHEEP");
        sheepButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                Sheep organism = new Sheep(organisms.GetHead().world, cords.x, cords.y);
                organisms.add(organism);
                organisms.Collision(organism);
                ClearBoard();
                organisms.DrawOrganism();
                Window window = JOptionPane.getRootFrame();
                window.dispose();
            }
        });
        JButton turtleButton = new JButton("TURTLE");
        turtleButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                Turtle organism = new Turtle(organisms.GetHead().world, cords.x, cords.y);
                organisms.add(organism);
                organisms.Collision(organism);
                ClearBoard();
                organisms.DrawOrganism();
                Window window = JOptionPane.getRootFrame();
                window.dispose();
            }
        });
        JButton wolfButton = new JButton("WOLF");
        wolfButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                Wolf organism = new Wolf(organisms.GetHead().world, cords.x, cords.y);
                organisms.add(organism);
                organisms.Collision(organism);
                ClearBoard();
                organisms.DrawOrganism();
                Window window = JOptionPane.getRootFrame();
                window.dispose();
            }
        });

        Object[] options ={antelopeButton,bilberryButton,cyberSheepButton,dandelionSheepButton,
                foxButton,grassButton,guaranaButton,hogweedButton,
                sheepButton,turtleButton,wolfButton,"Back"};
        JOptionPane.showOptionDialog(null,"CHOOSE ANIMAL YOU WANT TO ADD","ADD",
                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                null,options,options[0]);
    }
    private void Arrows(JPanel panel){
        //MAKING BUTTONS
        JButton upButton = new JButton("\u2191");
        upButton.setBackground(new Color(0xD7E0EB));

        JButton downButton = new JButton("\u2193");
        downButton.setBackground(new Color(0xD7E0EB));

        JButton leftButton = new JButton("\u2190");
        leftButton.setBackground(new Color(0xD7E0EB));

        JButton rightButton = new JButton("\u2192");
        rightButton.setBackground(new Color(0xD7E0EB));

        JButton saveButton = new JButton("SAVE");
        saveButton.setBackground(new Color(0xD7E0EB));

        JButton loadButton = new JButton("LOAD");
        loadButton.setBackground(new Color(0xD7E0EB));

        JButton quitButton = new JButton("QUIT");
        quitButton.setBackground(new Color(0xD7E0EB));

        JButton abilityButton = new JButton("ABILITY");
        abilityButton.setName("ABILITY");
        if(!superpower){
            abilityButton.setBackground(new Color(0xEB1404));
        }
        else abilityButton.setBackground(new Color(0x16EB2E));


        ArrowPanel.setBackground(new Color(0xEAF4FF));
        ArrowPanel.add(saveButton);
        ArrowPanel.add(upButton);
        ArrowPanel.add(loadButton);
        ArrowPanel.add(leftButton);
        ArrowPanel.add(abilityButton);
        ArrowPanel.add(rightButton);
        ArrowPanel.add(new JLabel());
        ArrowPanel.add(downButton);
        ArrowPanel.add(quitButton);
        panel.add(ArrowPanel,BorderLayout.CENTER);
        //MOVING UP
        upButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                Polozenie tmp_cords = organisms.Human();
                if(tmp_cords.x!=0){
                    if(tmp_cords.y > 1){
                        SetNextMove(Stale.UP);
                        MakeRound();
                    }
                }
                else {
                    MakeRound();
                }
            }
        });
        //MOVING DOWN
        downButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                Polozenie tmp_cords = organisms.Human();
                if(tmp_cords.x!=0){
                    if(tmp_cords.y < height){
                        SetNextMove(Stale.DOWN);
                        MakeRound();
                    }
                }
                else{
                    MakeRound();
                }
            }
        });
        //MOVING LEFT
        leftButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                Polozenie tmp_cords = organisms.Human();
                if(tmp_cords.x!=0){
                    if(tmp_cords.x > 1){
                        SetNextMove(Stale.LEFT);
                        MakeRound();
                    }
                }
                else{
                    MakeRound();
                }
            }
        });
        //MOVING RIGHT
        rightButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                Polozenie tmp_cords = organisms.Human();
                if(tmp_cords.x!=0){
                    if(tmp_cords.x < width){
                        SetNextMove(Stale.RIGHT);
                        MakeRound();
                    }
                }
                else{
                    MakeRound();
                }
            }
        });

        saveButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                SaveWorld();
            }
        });
        quitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                frame.dispose();
            }
        });
        loadButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                LoadWorld();
            }
        });
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(GetRounds()>10){
                    SetSuperpower(true);
                    abilityButton.setBackground(new Color(0x16EB2E));
                    SetNextMove(Stale.VEGETABLE);
                    MakeRound();
                }
            }
        };

        //SETTING ABILITY ON
        abilityButton.addActionListener(actionListener);
    }
    public void TurnOffSuperpower(){
        Component[] components = ArrowPanel.getComponents();
        for(Component component : components){
            if((component.getName()!=null)&&(component.getName().equals("ABILITY"))){
                JButton foundButton = (JButton)component;
                foundButton.setBackground(new Color(0xEB1404));
                break;
            }
        }
    }
    private void SaveWorld(){
        try{
            PrintWriter writer = new PrintWriter(new FileWriter("VirtualWorld.txt"));
                writer.printf("%d\n", height);
                writer.printf("%d\n", width);
                if(!superpower){
                    writer.printf("0\n");
                }
                else{
                    writer.printf("1\n");
                }
                writer.printf("%d\n", rounds);
                organisms.SaveOrganisms(writer);
            writer.close();
            System.out.println("SAVED TO FILE");
        }catch (IOException e){
            System.out.println("ERROR WITH SAVING DATA");
        }
    }
    private void LoadWorld(){
        try{
            File file = new File("VirtualWorld.txt");
            Scanner scanner = new Scanner(file);
            height = scanner.nextInt();
            width = scanner.nextInt();

            int bool_superpower = scanner.nextInt();
            if(bool_superpower==1){
                superpower = true;
            }
            else{
                superpower = false;
            }
            SetRounds(scanner.nextInt());
            organisms.LoadOrganisms(scanner);
            scanner.close();
            frame.getContentPane().removeAll();
            ArrowPanel.removeAll();
            TextArea.removeAll();
            frame.dispose();

            CreateBoard2();
            System.out.println("LOADED FROM FILE");

        }catch (IOException e){
            System.out.println("ERROR WITH LOADING DATA");
        }
    }
    //SETTERS
    public void SetRounds(int number){
        rounds = number;
    }
    public void SetSuperpower(boolean number){
        superpower = number;
    }
    public void SetNextMove(int number){
        NextMove = number;
    }
    public void SetWidth(int number){
        width = number;
    }
    public void SetHeight(int number){
        height = number;
    }

    //GETTERS
    private Polozenie GetButton(JButton button){
        Polozenie cords = new Polozenie(0,0);
        for(int i=0;i<height;i++){
            if(cords.x!=0){
                break;
            }
            for(int j=0;j<width;j++){
                if(board[i][j]==button){
                    cords = new Polozenie(j+1,i+1);
                    break;
                }
            }
        }
        return cords;
    }
    public JButton GetButton(Polozenie cords){
        return board[cords.y - 1][cords.x - 1];
    }
    public int GetRounds(){
        return rounds;
    }
    public boolean GetSuperpower(){
        return superpower;
    }
    public int GetNextMove(){
        return NextMove;
    }
    public int GetWidth(){
        return width;
    }
    public int GetHeight(){
        return height;
    }
    public ListOfOrganisms GetOrganisms(){
        return organisms;
    }

}
