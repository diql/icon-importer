package org.diql.iconimporter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class IconImporterDialog extends JDialog {
    private static final String TAG = "IconImporterDialog";
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField mSourceTextField;
    private JButton mImportButton;
    private JTextField mTargetTextField;
    private JButton mTargetButton;
    private JTextField mPrefixTextField;

    private String[] mImagePaths;
    private String mSourcePath;
    private String mTargetPath;
    private char mLink = '\\';
    private String xhdpi = "@2x";
    private String xxhdpi = "@3x";
    private String xxxhdpi = "@4x";

    private String drawable = "drawable";
    private String drawableXhdpi = "drawable-xhdpi";
    private String drawableXXhdpi = "drawable-xxhdpi";
    private String drawableXXXhdpi = "drawable-xxxhdpi";

    private String mPrefix = "";

    /**
     * 文件选择器.
     */
    private JFileChooser mJFileChooser;

    public IconImporterDialog() {
        setTitle("IconImporter");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        mJFileChooser = new JFileChooser();
        // 文件选择器的初始目录定为c盘
//        mJFileChooser.setCurrentDirectory(new File("c://"));
        initView();
    }

    private void initView() {
        int width = 400;
        int height = 300;
        int screenWidth = (int) getToolkit().getScreenSize().getWidth();
        int screenHeight = (int) getToolkit().getScreenSize().getHeight();
        setLocation((screenWidth - width)/2, (screenHeight - height) / 2);
        setSize(width, height);
//        setBounds(10, 10, 600, 400);
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        mImportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onImport(true);
            }
        });

        mTargetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onImport(false);
            }
        });

    }

    private void onImport(boolean isSource) {
        log("onImport(). isSource:" + isSource);
        mJFileChooser.setFileSelectionMode(1);
        int state = mJFileChooser.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
        if (state == 1) {
            log("用户取消选择.");
            return;
        } else {
            File f = mJFileChooser.getSelectedFile();// f为选择到的目录
            String path = f.getAbsolutePath();
            if (path.contains("/")) {
                mLink = '/';
            }
            if (isSource) {
                mSourcePath = path;
                mSourceTextField.setText(mSourcePath);
                loadAllImages(f);
            } else {
                mTargetPath = path;
                mTargetTextField.setText(mTargetPath);
            }
        }
    }

    private void loadAllImages(File directory) {
        log("loadAllImages()" + directory);
        if (directory == null) {
            log("directory is null.");
            return;
        }
        if (directory.isDirectory()) {
            String[] files = directory.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if (name != null && (name.endsWith(".jpg") || name.endsWith(".png"))) {
                        return true;
                    }
                    return false;
                }
            });
            mImagePaths = files;
            log(Arrays.toString(mImagePaths));
            if (mImagePaths == null) {
                log("mImagePaths is null.");
                return;
            }
        }
    }

    /**
     * 暂时不使用,没有意义.
     */
    private void onImportFile() {
        log("onImportFile");
        mJFileChooser.setFileSelectionMode(0);// 设定只能选择到文件
        mJFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f != null && (f.getAbsolutePath().endsWith(".jpg") || f.getAbsolutePath().endsWith(".png"))) {
                    log(f.getAbsolutePath());
                    return true;
                }
                return false;
            }

            @Override
            public String getDescription() {
                return "only select png & jpg.";
            }
        });
        int state = mJFileChooser.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
        if (state == 1) {
            return;
        } else {
            File f = mJFileChooser.getSelectedFile();// f为选择到的目录
            mSourceTextField.setText(f.getAbsolutePath());
        }
    }

    private void onOK() {
        // add your code here
        if (dealResult()) {
            dispose();
        }

    }

    private boolean dealResult() {
        log("dealResult().");
        if (mImagePaths == null) {
            return true;
        }
        mSourcePath = mSourceTextField.getText().trim();
        if (StringUtils.isEmpty(mSourcePath)) {
            log("isEmpty(mSourcePath)");
//            return false;
            return true;
        }
        mTargetPath = mTargetTextField.getText().trim();
        if (StringUtils.isEmpty(mTargetPath)) {
            log("isEmpty(mTargetPath)");
//            return false;
            return true;
        }
        for (String name : mImagePaths) {
            String source = mSourcePath + mLink + name;
            String targetDirctory = null;
            String targetName = name;
            if (name.contains(xhdpi)) {
                targetDirctory = mTargetPath + mLink + drawableXhdpi;
                targetName = name.replace(xhdpi, "");
            } else if (name.contains(xxhdpi)) {
                targetDirctory = mTargetPath + mLink + drawableXXhdpi;
                targetName = name.replace(xxhdpi, "");
            } else if (name.contains(xxxhdpi)) {
                targetDirctory = mTargetPath + mLink + drawableXXXhdpi;
                targetName = name.replace(xxxhdpi, "");
            } else {
                targetDirctory = mTargetPath + mLink + drawable;
            }
            if (!FileUtils.makeDirs(targetDirctory)) {
                continue;
            }

            mPrefix = mPrefixTextField.getText().trim();
            if (!StringUtils.isEmpty(mPrefix)) {
                targetName = mPrefix + targetName;
            }
            targetName = StringUtils.reginToResourceName(targetName);
            String target = targetDirctory + mLink + targetName;
            FileUtils.nioTransferCopy(source, target);
        }
        return true;
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void showMsgDialog(String msg) {

    }

    public static void main(String[] args) {
        IconImporterDialog dialog = new IconImporterDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }


    
    private static void log(String msg) {
        Log.i(TAG, msg);
    }
}
