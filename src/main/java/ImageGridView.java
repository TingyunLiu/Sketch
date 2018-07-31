import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ImageGridView extends JPanel implements Observer {
    private ImageModel imageModel;

    private JButton resetRating;

    public ImageGridView(ImageModel imageModel) {
        this.imageModel = imageModel;
        imageModel.addObserver(this);

        this.setPreferredSize(new Dimension(250, 320));

        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

        Image image = new Image(imageModel.getFilePath(), imageModel.getFileName(), true);
        this.add(image);
        JLabel imageName = new JLabel(imageModel.getFileName());
        imageName.setFont(new Font("Serif", Font.BOLD, 16));
        imageName.setPreferredSize(new Dimension(50, 16));
        JLabel imageDate = new JLabel(imageModel.getCreationDate());
        resetRating = new JButton(new ImageIcon(getClass().getResource("resetSmall.png")));
        resetRating.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                imageModel.setRating(0);
            }
        });
        if (imageModel.getRating() == 0)  {
            resetRating.setEnabled(false);
        } else {
            resetRating.setEnabled(true);
        }
        RatingStars ratingStars = new RatingStars(null, imageModel);

        Box b1 = Box.createHorizontalBox();
        b1.add(imageName, BorderLayout.WEST);
        b1.add(Box.createHorizontalStrut(30));
        b1.add(imageDate, BorderLayout.EAST);

        Box b2 = Box.createHorizontalBox();
        b2.add(ratingStars, BorderLayout.WEST);
        b2.add(resetRating, BorderLayout.EAST);

        Box b3 = Box.createVerticalBox();
        b3.add(Box.createVerticalStrut(10));
        b3.add(b1);
        b3.add(Box.createVerticalStrut(5));
        b3.add(b2);

        this.add(b3, BorderLayout.SOUTH);
    }

    public void update(Object observable) {
        if (imageModel.getRating() == 0) {
            this.resetRating.setEnabled(false);
        } else {
            this.resetRating.setEnabled(true);
        }
    }
}
