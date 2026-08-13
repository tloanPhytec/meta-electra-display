# Meta-Electra-Display

This Yocto Meta Layer serves to provide a Touch Display solution for the phyCORE-AM64x Development Kit, which is traditionally used in headless applications due to it's lack of graphics accelerators and dedicated display interfaces.

## Requirements

An 800x480 5-Inch EVE Capacitive Touchscreen TFT Display from Crystalfontz (Part Number: CFA800480E3-050SC) was selected due to it's integration of a Bridgetek BT817 EVE chip. 'EVE'stands for Embedded Video Engine and can be thought of as an external "graphics controller", you talk to them over SPI and they can manage the TFT display, touch, backlight, and even audio:

https://www.crystalfontz.com/product/cfa800480e3050sc-800x480-5-inch-capacitive-touchscreen-eve-tft-display

In order to connect this display panel to the phyCORE-AM64x Development Kit directly, you will also need this breakout board and 30pos FFC ribbon cable (along with about 7x M-F Jumper/Breadboard wires):

https://www.crystalfontz.com/product/cfa10098-eve-breakout-board

This was tested with BSP-Yocto-Ampliphy-AM64x-PD25.2.1

## BSP Integration

In order to evaluate this Meta Layer on your phyCORE-AM64x Development Kit, you must have first built the default BSP per the following development guide:

https://docs.phytec.com/projects/yocto-phycore-am64x/en/bsp-yocto-ampliphy-am64x-pd25.2.1/developingwithyocto/buildBSP.html

Navigate to your BSP's sources directory:

```sh
cd $BUILDDIR/../sources
```

Clone this repo and branch:

```sh
git clone https://github.com/tloanPhytec/meta-electra-display.git -b scarthgap
```

Enable the layer in your build:

```sh
cd $BUILDDIR
bitbake-layers add-layer ../sources/meta-electra-display
```

Rebuild your target's image with bitbake:

```sh
MACHINE=phyboard-electra-am64xx-2 DISTRO=ampliphy bitbake phytec-lvgl-image
```
