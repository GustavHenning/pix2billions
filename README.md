# pix2billions
Turns any image into a map for the game "They Are Billions"

# What is this?

A reddit user named ash47 created a <a href="https://github.com/ash47/TheyAreBillionsModKit">mod kit</a> for the game "They are Billions". He then showed that he could make maps from image files given a specific format. I extended upon his idea by creating a program that generates a map given any image.

Please note: This project must not be used for illegal purposes, or for getting a highscore on any leaderboards that are present in the game, it is strictly for educational purposes and for people to experiment with.

![map-gaben](https://github.com/gustavhenning/pix2billions/blob/master/examples-output/map-gaben.png)
![gaben](https://github.com/gustavhenning/pix2billions/blob/master/examples/gaben.png)

![map-world](https://github.com/gustavhenning/pix2billions/blob/master/examples-output/map-world.png)
![world](https://github.com/gustavhenning/pix2billions/blob/master/examples/world.png)

![map-world-flipped](https://github.com/gustavhenning/pix2billions/blob/master/examples-output/map-world-flipped.png)
![world-flipped](https://github.com/gustavhenning/pix2billions/blob/master/examples/world-flipped.png)


# How do I use it?

The jar file in this repository (<a href="https://github.com/GustavHenning/pix2billions/releases/">pix2billions.jar</a> in the root directory) takes the paths of images and outputs the map equivalents in the same directory. Naming convention for the output of image.png is map-image.png

Make sure you dont forget to install java and any other dependencies to run jar files first.

# Example

0) make sure you got java installed
1) open a terminal and navigate to your folder where the pix2billions.jar is located
2) enter "java -jar pix2billions.jar myimage.png" (given that the image is in the same directory)
3) enjoy your map-myimage.png file

# FAQ

Q: Is there an optimal resolution for input images?

A: Yes, it's 150x200 px



Q: Why the tilted map? 

A: No idea, see <a href="https://www.reddit.com/r/TheyAreBillions/comments/7mlvfc/modded_custom_spiral_choke_challenge_map_download/druwa1h/">this thread</a>

# What's next?

Feel free to write own distance function to alter what colors correspond to what type of land by forking this project or implementing it in another language! gg!
