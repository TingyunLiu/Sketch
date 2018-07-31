
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

/**
 * Class flip between canvas (ViewDraw) and more colors option (ViewChooseColor)
 */

public class ImageCollectionView extends JPanel implements Observer {

    private ImageCollectionModel collectionModel;
    private ImageCollectionGridView collectionGridView;
    private ImageCollectionListView collectionListView;

    /**
     * Create a new View.
     */
    public ImageCollectionView(ImageCollectionModel collectionModel, ImageCollectionGridView collectionGridView,
                               ImageCollectionListView collectionListView) {

        this.collectionModel = collectionModel;
        collectionModel.addObserver(this);

        this.collectionGridView = collectionGridView;
        this.collectionListView = collectionListView;

        CardLayout layout = new CardLayout();
        this.setLayout(layout);
        this.add(collectionGridView, "GRID");
        this.add(collectionListView, "LIST");
        if (collectionModel.getIsGridLayout()) {
            layout.show(this, "GRID");
        } else {
            layout.show(this, "LIST");
        }

        ImageCollectionView pane = this;
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (collectionModel.getIsGridLayout()) {
                    pane.setPreferredSize(new Dimension(200, pane.calculateGridHeight()));
                }
            }
        });
    }

    private int calculateGridHeight() {
        int numColumns = this.getWidth() / 310;
        numColumns = (numColumns == 0) ? 1 : numColumns;
        return (collectionModel.numDisplayableImages() + numColumns - 1) / numColumns * 370 + 50;
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        CardLayout layout = (CardLayout)this.getLayout();
        if (this.collectionModel.getIsGridLayout()) {
            layout.show(this, "GRID");
            this.setPreferredSize(new Dimension(200, this.calculateGridHeight()));
            collectionGridView.update(observable);
        } else {
            layout.show(this, "LIST");
            int height = collectionModel.numDisplayableImages() * 250 + 30;
            this.setPreferredSize(new Dimension(200, height));
            collectionListView.update(observable);
        }
    }
}