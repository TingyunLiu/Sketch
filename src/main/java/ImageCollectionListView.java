
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ImageCollectionListView extends JPanel implements Observer {

    private ImageCollectionModel collectionModel;

    private java.util.List<ImageListView> images;

    public ImageCollectionListView(ImageCollectionModel collectionModel) {

        this.collectionModel = collectionModel;

        this.images = new ArrayList();

        FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5000, 30);
        this.setLayout(layout);
    }

    private void addNewImageListView(ImageModel imageModel) {
        ImageListView imageListView = new ImageListView(imageModel);
        this.images.add(imageListView);
        this.add(imageListView);
    }

    public void update(Object observable) {
        if (collectionModel.isAddingNewImage()) {
            ImageModel imageModel = collectionModel.getImageAt(collectionModel.numImages()-1);
            this.addNewImageListView(imageModel);
        } else {
            this.removeAll();
            this.images = new ArrayList();
            for (int i = 0; i < collectionModel.numImages(); i++) {
                ImageModel imageModel = collectionModel.getImageAt(i);
                if (imageModel.getRating() >= collectionModel.getRatingStarFilter()) {
                    this.addNewImageListView(imageModel);
                }
            }
        }
        this.repaint();
        this.revalidate();
    }
}
