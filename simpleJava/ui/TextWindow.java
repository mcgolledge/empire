/*******************************************************************************
 * Copyright 2013 M. Chris Golledge
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package golledge.empire.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextWindow extends JFrame implements Runnable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    JTextArea text;
    Thread runner;

    public TextWindow()
    {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
    }

    public TextWindow(String title, String text)
    {
        init();
        this.text.setText(text);
        setTitle(title);
    }

    public void init()
    {
        text = new JTextArea();
        text.setBackground(Color.WHITE);
        text.setColumns(60);
        text.setRows(20);
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(text);
        scrollPane.setBounds(new Rectangle(50, 50, 500, 750));
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        scrollPane
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setLocationRelativeTo(null);
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    TextWindow frame = new TextWindow(
                            "The Story of Goldilocks and the Three Bears",
                            "Once upon a time, there was a little girl named Goldilocks.  She  went for a walk in the forest.  Pretty soon, she came upon a house.  She knocked and, when no one answered, she walked right in.\r\n"
                                    + ""
                                    + "\r\n"
                                    + "At the table in the kitchen, there were three bowls of porridge. Goldilocks was hungry.  She tasted the porridge from the first bowl.\r\n"
                                    + "\r\n"
                                    + "\"This porridge is too hot!\" she exclaimed.\r\n"
                                    + "\r\n"
                                    + "So, she tasted the porridge from the second bowl.\r\n"
                                    + "\r\n"
                                    + "\"This porridge is too cold,\" she said\r\n"
                                    + "\r\n"
                                    + "So, she tasted the last bowl of porridge.\r\n"
                                    + "\r\n"
                                    + "\"Ahhh, this porridge is just right,\" she said happily and she ate it all up.\r\n"
                                    + "\r\n"
                                    + "After she'd eaten the three bears' breakfasts she decided she was feeling a little tired.  So, she walked into the living room where she saw three chairs.  Goldilocks sat in the first chair to rest her feet.  \r\n"
                                    + "\r\n"
                                    + "\"This chair is too big!\" she exclaimed.\r\n"
                                    + "\r\n"
                                    + "So she sat in the second chair.\r\n"
                                    + "\r\n"
                                    + "\"This chair is too big, too!\"  she whined.\r\n"
                                    + "\r\n"
                                    + "So she tried the last and smallest chair.\r\n"
                                    + "\r\n"
                                    + "\"Ahhh, this chair is just right,\" she sighed.  But just as she settled down into the chair to rest, it broke into pieces!\r\n"
                                    + "\r\n"
                                    + "Goldilocks was very tired by this time, so she went upstairs to the bedroom.  She lay down in the first bed, but it was too hard. Then she lay in the second bed, but it was too soft. Then she lay down in the third bed and it was just right.  Goldilocks fell asleep.\r\n"
                                    + "\r\n"
                                    + "As she was sleeping, the three bears came home.\r\n"
                                    + "\r\n"
                                    + "\"Someone's been eating my porridge,\" growled the Papa bear.\r\n"
                                    + "\r\n"
                                    + "\"Someone's been eating my porridge,\" said the Mama bear.\r\n"
                                    + "\r\n"
                                    + "\"Someone's been eating my porridge and they ate it all up!\" cried the Baby bear.\r\n"
                                    + "\r\n"
                                    + "\"Someone's been sitting in my chair,\" growled the Papa bear.\r\n"
                                    + "\r\n"
                                    + "\"Someone's been sitting in my chair,\" said the Mama bear.\r\n"
                                    + "\r\n"
                                    + "\"Someone's been sitting in my chair and they've broken it all to pieces,\" cried the Baby bear.\r\n"
                                    + "\r\n"
                                    + "They decided to look around some more and when they got upstairs to the bedroom, Papa bear growled, \"Someone's been sleeping in my bed,\"\r\n"
                                    + "\r\n"
                                    + "\"Someone's been sleeping in my bed, too\" said the Mama bear\r\n"
                                    + "\r\n"
                                    + "\"Someone's been sleeping in my bed and she's still there!\" exclaimed Baby bear.\r\n"
                                    + "\r\n"
                                    + "Just then, Goldilocks woke up and saw the three bears.  She screamed, \"Help!\"  And she jumped up and ran out of the room.  Goldilocks ran down the stairs, opened the door, and ran away into the forest.  And she never returned to the home of the three bears.\r\n"
                                    + "");
                    frame.pack();
                    frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public void run()
    {
        runner = new Thread();
        runner.start();
        pack();
        setVisible(true);
    }

}
