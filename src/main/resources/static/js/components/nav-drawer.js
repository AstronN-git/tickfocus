function toggleNavigationDrawer() {
    if (typeof this.navigationDrawerState == 'undefined')
        this.navigationDrawerState = false

    this.navigationDrawerState = !this.navigationDrawerState

    console.log(this.navigationDrawerState)

    const navigationDrawer = document.getElementById("navigation-drawer");
    const navigationDrawerShading = document.getElementById("navigation-drawer-shading");

    if (this.navigationDrawerState) {
        navigationDrawer.style.display = 'block'
        navigationDrawerShading.style.display = 'block'
    } else {
        navigationDrawer.style.display = 'none'
        navigationDrawerShading.style.display = 'none'
    }
}