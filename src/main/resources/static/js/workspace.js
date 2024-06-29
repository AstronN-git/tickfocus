let progress = 1

const progressEpsHigh = .005;

const sliceActive = document.getElementById("slice-active")
const sliceTrack = document.getElementById("slice-track")

function updateProgress(progress) {
    if (progress >= 1) {
        progress = 1;
        document.forms["end-timer"].submit()
    }

    if (progress < 0) {
        progress = 0;
    }

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

setInterval(() => {
    if (!isTimerStarted)
        return

    let date = Date.now()
    let diff = date - timerStartDate

    let percentage = diff / fullTimerDuration
    updateProgress(percentage)
}, 100)