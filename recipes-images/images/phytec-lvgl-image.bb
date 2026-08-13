require recipes-images/images/phytec-headless-image.bb

IMAGE_INSTALL:append = " lvgl-demo-electra"

COMPATIBLE_MACHINE = "phyboard-electra-am64xx-2"
