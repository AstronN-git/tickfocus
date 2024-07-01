let navigationDrawerState = false;

function updateProgress(progress) {
    const progressEpsHigh = .005;

    if (progress >= 1) {
        progress = 1;
        document.forms["end-timer"].submit()
    }

    if (progress < 0) {
        progress = 0;
    }

    const sliceActive = document.getElementById("slice-active")
    const sliceTrack = document.getElementById("slice-track")

    if (progress + progressEpsHigh >= 1) {
        sliceActive.classList.remove('active-indicator-none')
        sliceActive.classList.add('active-indicator-done')
        sliceTrack.classList.add('track-and-stop-done')

        sliceActive.style.width = '100%'

        return
    }

    if (progress <= 0) {
        sliceActive.classList.remove('active-indicator')
        sliceActive.classList.add('active-indicator-none')

        sliceTrack.style.width = '100%'

        return
    }

    sliceActive.classList.remove('active-indicator-none')
    sliceActive.classList.add('active-indicator')

    sliceActive.style.width = (progress * 100) + '%'
    sliceTrack.style.width = (1 - progress) * 100 + '%'
}

function updateTimeLeft(ms) {
    const timeText = document.getElementById("time-left")

    let seconds = Math.trunc((ms / 1000) % 60)
    let minutes = Math.trunc(ms / 60000)

    if (minutes > 0) {
        timeText.innerText = minutes + 'min ' + seconds + 's left'
    } else {
        timeText.innerText = seconds + 's left'
    }
}

function toggleNavigationDrawer() {
    navigationDrawerState = !navigationDrawerState

    console.log(navigationDrawerState)

    const navigationDrawer = document.getElementById("navigation-drawer");
    const navigationDrawerShading = document.getElementById("navigation-drawer-shading");

    if (navigationDrawerState) {
        navigationDrawer.style.display = 'block'
        navigationDrawerShading.style.display = 'block'
    } else {
        navigationDrawer.style.display = 'none'
        navigationDrawerShading.style.display = 'none'
    }
}

setInterval(() => {
    if (!isTimerStarted)
        return

    let date = Date.now()
    let diff = date - timerStartDate

    let percentage = diff / fullTimerDuration
    updateProgress(percentage)
    updateTimeLeft(fullTimerDuration - diff)
}, 100)