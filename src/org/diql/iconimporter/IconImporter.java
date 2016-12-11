package org.diql.iconimporter;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Created by diql on 2016/12/8.
 */
public class IconImporter extends AnAction {
    private static final String TAG = "org.diql.iconimporter.IconImporter";
    private IconImporterDialog mDialog;
    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        if (mDialog == null) {
            mDialog = new IconImporterDialog();
        }
        log(mDialog.toString());
        mDialog.setVisible(true);
    }

    private static void log(String msg) {
        Log.i(TAG, msg);
    }
}
