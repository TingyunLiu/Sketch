import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image extends JPanel {

    private boolean enlargedExist;
    private String filePath;

    public Image(String filePath, String fileName, Boolean allowEnlarge) {

        if (allowEnlarge) this.setBorder(BorderFactory.createLineBorder(Color.black));

        this.filePath = filePath;
        this.enlargedExist = false;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (enlargedExist || !allowEnlarge) return;
                JFrame enlargedImage = new JFrame();
                enlargedImage.setTitle(fileName);
                enlargedImage.setSize(800, 600);
                enlargedImage.setMaximumSize(new Dimension(800, 600));
                enlargedImage.setMinimumSize(new Dimension(400, 300));
                enlargedImage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                enlargedImage.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        enlargedExist = false;
                    }
                });
                enlargedImage.add(new Image(filePath, fileName, false));
                enlargedImage.setVisible(true);
                enlargedExist = true;
            }
        });
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File(this.filePath));
        } catch (IOException e) {
            System.out.println("IO Exception when loading image " + this.filePath);
            e.printStackTrace();
        }

        // resize image to keep aspect ratio
        int oldWidth = bufferedImage.getWidth();
        int oldHeight = bufferedImage.getHeight();
        int newWidth = this.getWidth();
        int newHeight = this.getHeight();
        double imageAspectRatio = (double)oldWidth / oldHeight;
        double panelAspectRatio = (double)this.getWidth() / this.getHeight();
        if (panelAspectRatio > imageAspectRatio) {
            newWidth = (int)(oldWidth * ((double)this.getHeight()/oldHeight));
        } else {
            newHeight = (int)(oldHeight * ((double)this.getWidth()/oldWidth));
        }
        int x = (this.getWidth() - newWidth) / 2;
        int y = (this.getHeight() - newHeight) / 2;

        g2.drawImage(bufferedImage, x, y, newWidth, newHeight, null);
    }
}
