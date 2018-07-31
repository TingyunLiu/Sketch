import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class RatingStars extends JPanel implements Observer {

    private JLabel star1;
    private JLabel star2;
    private JLabel star3;
    private JLabel star4;
    private JLabel star5;

    private java.util.List<JLabel> stars;

    private ImageCollectionModel imageCollectionModel;
    private ImageModel imageModel;

    public RatingStars(ImageCollectionModel imageCollectionModel, ImageModel imageModel) {

        this.imageCollectionModel = imageCollectionModel;
        this.imageModel = imageModel;

        if (imageCollectionModel != null) {
            imageCollectionModel.addObserver(this);
        } else {
            imageModel.addObserver(this);
        }

        star1 = new JLabel(new ImageIcon(getClass().getResource("unstar.png")));
        star2 = new JLabel(new ImageIcon(getClass().getResource("unstar.png")));
        star3 = new JLabel(new ImageIcon(getClass().getResource("unstar.png")));
        star4 = new JLabel(new ImageIcon(getClass().getResource("unstar.png")));
        star5 = new JLabel(new ImageIcon(getClass().getResource("unstar.png")));

        stars = new ArrayList();

        stars.add(star1);
        stars.add(star2);
        stars.add(star3);
        stars.add(star4);
        stars.add(star5);

        for (JLabel star: stars) {
            star.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int rating = stars.indexOf(star)+1;
                    if (imageCollectionModel != null) {
                        imageCollectionModel.setRatingStarFilter(rating);
                    } else {
                        imageModel.setRating(rating);
                    }
                }
            });
            this.add(star);
        }

        if (imageCollectionModel != null) {
            this.update(imageCollectionModel);
        } else {
            this.update(imageModel);
        }
    }

    public void update(Object observable) {
        int i;
        for (i = 0; i < ((imageCollectionModel != null) ? imageCollectionModel.getRatingStarFilter() :
                imageModel.getRating()); i++) {
            stars.get(i).setIcon(new ImageIcon(getClass().getResource("star.png")));
        }
        while (i < 5) {
            stars.get(i).setIcon(new ImageIcon(getClass().getResource("unstar.png")));
            i++;
        }
    }
}