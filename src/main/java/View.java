
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class View extends JFrame {

    private ImageCollectionModel collectionModel;

    /**
     * Create a new View.
     */
    public View(ImageCollectionModel model) {
        // Set up the window.
        this.setTitle("Fotag");
        this.setMinimumSize(new Dimension(600, 450));
        this.setSize(1000, 830);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

        this.collectionModel = model;

        String fileName = "src/main/data.bin";

        File file = new File(fileName);
        if (file.exists() && !file.isDirectory()) { // load model
            try {
                FileInputStream fileInput = new FileInputStream(fileName);
                ObjectInputStream stream = new ObjectInputStream(fileInput);
                ImageCollectionModel newModel = (ImageCollectionModel)stream.readObject();
                stream.close();
                fileInput.close();
                collectionModel.loadModel(newModel);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        Toolbar toolbar = new Toolbar(collectionModel);
        this.add(toolbar, BorderLayout.NORTH);

        ImageCollectionGridView collectionGridView = new ImageCollectionGridView(collectionModel);
        ImageCollectionListView collectionListView = new ImageCollectionListView(collectionModel);
        ImageCollectionView collectionView = new ImageCollectionView(collectionModel, collectionGridView, collectionListView);

        JScrollPane scrollPane = new JScrollPane(collectionView);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    FileOutputStream fileOutput = new FileOutputStream(fileName);
                    ObjectOutputStream stream = new ObjectOutputStream(fileOutput);
                    stream.writeObject(collectionModel);
                    stream.close();
                    fileOutput.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        collectionModel.notifyObservers();

        setVisible(true);
    }
}
