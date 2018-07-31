
import java.util.*;
import java.io.Serializable;

public class ImageCollectionModel implements Serializable {
    /** The observers that are watching this model for changes. */
    private List<Observer> observers;

    private java.util.List<ImageModel> images;
    private int ratingStarFilter;
    private boolean addingNewImage;
    private boolean isGridLayout;

    /**
     * Create a new model.
     */
    public ImageCollectionModel() {
        this.observers = new ArrayList();

        this.images = new ArrayList();

        this.ratingStarFilter = 0;
        this.addingNewImage = false;
        this.isGridLayout = true;
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void addImage(String filePath, String fileName, String creationDate) {
        ImageModel image = new ImageModel(this, filePath, fileName, creationDate);
        this.images.add(image);
        this.addingNewImage = true;
        notifyObservers();
        this.addingNewImage = false;
    }

    public int numImages() { return this.images.size(); }

    public ImageModel getImageAt(int index) { return this.images.get(index); }

    public void setRatingStarFilter(int ratingStarFilter) {
        this.ratingStarFilter = ratingStarFilter;
        notifyObservers();
    }

    public int getRatingStarFilter() { return this.ratingStarFilter; }

    public boolean isAddingNewImage() { return this.addingNewImage; }

    public void setIsGridLayout(boolean value) {
        this.isGridLayout = value;
        notifyObservers();
    }

    public boolean getIsGridLayout() { return this.isGridLayout; }

    public void loadModel(ImageCollectionModel newModel) {
        this.ratingStarFilter = newModel.getRatingStarFilter();
        this.addingNewImage = false;
        this.isGridLayout = newModel.getIsGridLayout();
        this.images = newModel.images;
    }

    public int numDisplayableImages() {
        int count = 0;
        for (ImageModel image: this.images) {
            if (image.getRating() >= this.ratingStarFilter) count++;
        }
        return count;
    }

    public void deleteAll() {
        this.images.clear();
        this.notifyObservers();
    }

    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }
}
