
import java.util.*;
import java.io.Serializable;

public class ImageModel implements Serializable {
    /** The observers that are watching this model for changes. */
    private List<Observer> observers;

    private ImageCollectionModel collectionModel;
    private String filePath;
    private String fileName;
    private String creationDate;
    private int rating;

    /**
     * Create a new model.
     */
    public ImageModel(ImageCollectionModel collectionModel, String filePath, String fileName, String creationDate) {
        this.observers = new ArrayList();

        this.collectionModel = collectionModel;
        this.filePath = filePath;
        this.fileName = fileName;
        this.creationDate = creationDate;
        this.rating = 0;
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public String getFilePath() { return this.filePath; }

    public String getFileName() { return this.fileName; }

    public String getCreationDate() { return this.creationDate; }

    public int getRating() { return this.rating; }

    public void setRating(int rating) {
        this.rating = rating;
        if (rating < collectionModel.getRatingStarFilter()) {
            collectionModel.notifyObservers();
        }
        this.notifyObservers();
    }

    public ImageCollectionModel getCollectionModel() { return this.collectionModel; }

    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }
}

