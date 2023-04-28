/* (C)2023 */
package org.gui;

import java.util.concurrent.Callable;

public class BackButton extends Button {
    BackButton(Callable<Void> goBack) {
        super("<-");
        setBounds(20, 20, 40, 20);
        addActionListener(e -> {});
    }
}
