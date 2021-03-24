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

import javax.swing.text.JTextComponent;

public class TextSetter extends Thread
{
    JTextComponent field;
    // int delay; // for sleeping
    String text;

    public TextSetter(JTextComponent field, String text)
    {
        this.field = field;
        this.text = text;
    }

    public void run()
    {
        try
        {
            field.setText(text);
            field.paintImmediately(field.getVisibleRect());
        }
        catch (Exception e)
        {
        }
    }
}
