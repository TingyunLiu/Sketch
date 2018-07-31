
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class ImageCollectionGridView extends JPanel implements Observer {

    private ImageCollectionModel collectionModel;

    private java.util.List<ImageGridView> images;

    public ImageCollectionGridView(ImageCollectionModel collectionModel) {

        this.collectionModel = collectionModel;

        this.images = new ArrayList();

        FlowLayout layout = new FlowLayout(FlowLayout.LEADING, 60, 50);
        this.setLayout(layout);

        JPanel pane = this;
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (pane.getWidth() < 650) {
                    pane.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 50));
                } else {
                    pane.setLayout(new FlowLayout(FlowLayout.LEADING, 60, 50));
                }
            }
        });
    }

    private void addNewImageGridView(ImageModel imageModel) {
        ImageGridView imageGridView = new ImageGridView(imageModel);
        this.images.add(imageGridView);
        this.add(imageGridView);
    }

    public void update(Object observable) {
        if (this.getWidth() < 650) {
            this.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 50));
        } else {
            this.setLayout(new FlowLayout(FlowLayout.LEADING, 60, 50));
        }

        if (collectionModel.isAddingNewImage()) {
            ImageModel imageModel = collectionModel.getImageAt(collectionModel.numImages()-1);
            this.addNewImageGridView(imageModel);
        } else {
            this.removeAll();
            this.images = new ArrayList();
            for (int i = 0; i < collectionModel.numImages(); i++) {
                ImageModel imageModel = collectionModel.getImageAt(i);
                if (imageModel.getRating() >= collectionModel.getRatingStarFilter()) {
                    this.addNewImageGridView(imageModel);
                }
            }
        }
        this.repaint();
        this.revalidate();
    }
}
