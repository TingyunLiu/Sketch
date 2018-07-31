import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.lang.Object;
import java.util.*;
import java.io.File;


public class Toolbar extends JPanel implements Observer {

        private ImageCollectionModel collectionModel;

        private JToggleButton grid;
        private JToggleButton list;
        private JButton resetFilter;
        private JButton deleteAll;

        public Toolbar(ImageCollectionModel collectionModel) {
            this.setBorder(BorderFactory.createLineBorder(Color.black));

            this.collectionModel = collectionModel;
            collectionModel.addObserver(this);

            BorderLayout layout = new BorderLayout();
            this.setLayout(layout);

            grid = new JToggleButton(new ImageIcon(getClass().getResource("grid.png")));
            list = new JToggleButton(new ImageIcon(getClass().getResource("list.png")));
            deleteAll = new JButton("Delete All");
            JButton upload = new JButton(new ImageIcon(getClass().getResource("upload.png")));
            JLabel label = new JLabel("Filter By: ");
            label.setFont(new Font("Serif", Font.BOLD, 16));
            RatingStars filter = new RatingStars(collectionModel, null);
            resetFilter = new JButton(new ImageIcon(getClass().getResource("resetLarge.png")));

            grid.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    collectionModel.setIsGridLayout(true);
                }
            });

            list.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    collectionModel.setIsGridLayout(false);
                }
            });

            final ImageCollectionModel model = this.collectionModel;
            deleteAll.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    model.deleteAll();
                }
            });
            deleteAll.setEnabled(false);

            if (collectionModel.getIsGridLayout()) {
                grid.setSelected(true);
                list.setSelected(false);
            } else {
                grid.setSelected(false);
                list.setSelected(true);
            }

            JPanel parent = this;
            upload.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JFileChooser fc = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Picture","png", "jpg");
                    fc.setFileFilter(filter);
                    fc.setAcceptAllFileFilterUsed(false);
                    fc.setMultiSelectionEnabled(true);
                    int returnVal = fc.showOpenDialog(parent);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File[] files = fc.getSelectedFiles();
                        for (File file: files) {
                            String filePath = file.getAbsolutePath();
                            String fileName = file.getName();
                            Date creationDate = new Date(file.lastModified());
                            int year = creationDate.getYear() + 1900;
                            int month = creationDate.getMonth() + 1;
                            int day = creationDate.getDay() + 1;
                            String date = month + "/" + day + "/" + year;
                            collectionModel.addImage(filePath, fileName, date);
                        }
                    }
                }
            });

            resetFilter.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    collectionModel.setRatingStarFilter(0);
                }
            });
            resetFilter.setEnabled(false);

            Box b = Box.createVerticalBox();
            b.add(Box.createVerticalStrut(5));
            this.add(b, BorderLayout.NORTH);

            Box b1 = Box.createHorizontalBox();
            b1.add(Box.createHorizontalStrut(10));
            b1.add(grid);
            b1.add(Box.createHorizontalStrut(10));
            b1.add(list);
            b1.add(Box.createHorizontalStrut(10));
            b1.add(deleteAll);
            this.add(b1, BorderLayout.WEST);

            Box b2 = Box.createHorizontalBox();
            b2.add(upload);
            b2.add(Box.createHorizontalStrut(10));
            b2.add(label);
            b2.add(Box.createHorizontalStrut(10));
            b2.add(filter);
            b2.add(Box.createHorizontalStrut(10));
            b2.add(resetFilter);
            b2.add(Box.createHorizontalStrut(10));
            this.add(b2, BorderLayout.EAST);
        }

        public void update(Object observable) {
            if (this.collectionModel.getIsGridLayout()) {
                this.grid.setSelected(true);
                this.list.setSelected(false);
            } else {
                this.grid.setSelected(false);
                this.list.setSelected(true);
            }

            if (this.collectionModel.getRatingStarFilter() == 0) {
                this.resetFilter.setEnabled(false);
            } else {
                this.resetFilter.setEnabled(true);
            }

            if (this.collectionModel.numImages() == 0) {
                this.deleteAll.setEnabled(false);
            } else {
                this.deleteAll.setEnabled(true);
            }
        }
}
