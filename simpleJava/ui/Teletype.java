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

import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

public class Teletype extends JTextArea
{
    // pauses for reading messages or dramatic effect, in milliseconds
    private static final int VERY_SHORT = 100;
    private static final int MEDIUM_SHORT = 500;

    public Teletype()
    {
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static enum MessageType
    {
        INFORMATION, WARNING, BATTLE
    }

    public void setText(String text, Color color, boolean autoClear)
    {
        setForeground(color);
        for (int i = 1; i <= text.length(); i++)
        {
            TextSetter cf = new TextSetter((JTextComponent) this,
                    text.substring(0, i));
            cf.start();
            sleepSafe(VERY_SHORT);
        }
        if (autoClear)
            clear(MEDIUM_SHORT);
    }

    public void clear()
    {
        super.setText("");
    }

    private void clear(int delay)
    {
        TextSetter cf = new TextSetter((JTextComponent) this, "");
        cf.start();
        sleepSafe(delay);
    }

    public void sleepSafe(long msec)
    {
        try
        {
            Thread.sleep(msec);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
