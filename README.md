# Sketch

#### Developement Environment
    Machine tested: Mac
    Java version: jdk-10.0.1
    
    

#### Design
    Framework: MVC (two models and multiple views, where view and controller are combined)
        Whenever a sequence of changes performed on model by each view (through many setters), the model notify each
        observers associated with the model (by calling each observer's update). Then each view updates its most recent
        view to the display.
    Layout used: FlowLayout, BorderLayout, CardLayout
    Widgets used: JFrame, JPanel, JScrollPane, JButton, JToggleButton, JLabel, JFileChooser



#### Description
    A single-window application that users can upload and rate a set of images in 2 views: Grid and List.
    Initially, the window is empty, users can upload image by clicking the upload icon at the top right corner.
        Loaded image thumbnails are displayed in either Grid view or List View, users may click one toggle button at the
        upper left corner to switch between two views. Also, image thumbnails contain each image's name, last modified
        date. Users can click on an image to enlarge the image in a separate window, clicking the red 'x' at the upper
        left corner of the window will allow users to close the enlarged image window. Each image is kept aspect ratio
        with respect to the image original ratio. In addition, there is a rating machanism for users to rate each image,
        ranging from 1 star to 5 stars and initially no star. Users can also click stars at the upper right corner of
        the window to filter a set of images which have minimum rating. Users are able to clear a rating for each
        specific image and remove the display filter. When the window resizes in Grid view, the number of columns can
        vary from 1 to n, where n depends on the space available horizontally. When exiting the program, the set of
        images will be automatically saved so that same images are loaded and shown when relaunching next time. Users
        have ability to delete all images by clicking a button "Delete All" besides view-switching toggle buttons.
    
