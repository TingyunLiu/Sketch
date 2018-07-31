import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ImageListView extends JPanel implements Observer{
    private ImageModel imageModel;

    private JButton resetRating;

    public ImageListView(ImageModel imageModel) {
        this.imageModel = imageModel;
        imageModel.addObserver(this);

        this.setPreferredSize(new Dimension(550, 220));

        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

        Image image = new Image(imageModel.getFilePath(), imageModel.getFileName(), true);
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

        Box b2 = Box.createHorizontalBox();
        b2.add(imageDate, BorderLayout.WEST);

        Box b3 = Box.createHorizontalBox();
        b3.add(resetRating, BorderLayout.WEST);

        Box b4 = Box.createVerticalBox();
        b4.add(Box.createVerticalGlue());
        b4.add(b1);
        b4.add(Box.createVerticalStrut(10));
        b4.add(b2);
        b4.add(Box.createVerticalStrut(10));
        b4.add(ratingStars);
        b4.add(b3);
        b4.add(Box.createVerticalGlue());

        Box b5 = Box.createHorizontalBox();
        b5.add(image);
        b5.add(Box.createHorizontalStrut(10));
        b5.add(b4, BorderLayout.EAST);

        this.add(b5);
    }

    public void update(Object observable) {
        if (imageModel.getRating() == 0) {
            this.resetRating.setEnabled(false);
        } else {
            this.resetRating.setEnabled(true);
        }
    }
}
