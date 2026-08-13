SUMMARY = "LVGL Demo Application for Bridgetek EVE on AM64x"
HOMEPAGE = "https://github.com/Bridgetek/LVGL-Eve"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=24bf15f64f4fd6323c344bb40f53ddb1"

COMPATIBLE_MACHINE = "phyboard-electra-am64xx-2"

SRC_URI = "\
	gitsm://github.com/Bridgetek/LVGL-Eve.git;protocol=https;branch=main \
	file://0001-HAL-Updates.patch;patchdir=EveApps \
	file://lvgl.service \
"
SRCREV = "4ae1950703bd61624d0d748ae1916858c2a539d3"

S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE = " \
	-DEVE_APPS_PLATFORM=DEFAULT \
	-DEVE_APPS_GRAPHICS=BT817 \
	-DEVE_APPS_DISPLAY=EVE_DISPLAY_WVGA \
	-DEVE_PLATFORM=LINUX_SPI \
"

CFLAGS:append = " -Ulinux -Wno-error=format-security"
CXXFLAGS:append = " -Ulinux"

do_configure:prepend() {
    # Force LVGL to use standard Linux malloc instead of its tiny internal pool
    sed -i 's/#define LV_MEM_CUSTOM .*/#define LV_MEM_CUSTOM 1/g' ${S}/Demo_lvgl/lv_conf.h

    # Bump the internal pool size to 8MB
    sed -i 's/#define LV_MEM_SIZE .*/#define LV_MEM_SIZE (8192U * 1024U)/g' ${S}/Demo_lvgl/lv_conf.h
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/bin/Demo_lvgl ${D}${bindir}/lvgl

    install -d ${D}/${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/lvgl.service ${D}/${systemd_unitdir}/system
}

inherit systemd

RDEPENDS:${PN} += "bash"

SYSTEMD_SERVICE:${PN} = "lvgl.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

FILES:${PN} += " \
	${bindir}/lvgl \
	${systemd_unitdir}/system/lvgl.service \
"
