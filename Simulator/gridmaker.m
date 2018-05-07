img = imread('campus.png');  %# Load a sample 3-D RGB image
img(100:100:end,:,:) = 0;       %# Change every tenth row to black
img(:,100:100:end,:) = 0;       %# Change every tenth column to black
imshow(img);                  %# Display the image