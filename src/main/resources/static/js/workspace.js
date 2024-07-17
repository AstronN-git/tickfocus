let endTimerFormSubmitted = false

function createProgressBar(id, duration, startingTime) {
    const active = document.getElementById(id + "-active")
    const track = document.getElementById(id + "-track")
    const trackStop = document.getElementById(id + "-track-stop")

    active.style.animationDuration = duration;
    track.style.animationDuration = duration;
    trackStop.style.animationDuration = duration;

    if (typeof startingTime == 'string') {
        active.style.animationDelay = '-' + startingTime;
        track.style.animationDelay = '-' + startingTime;
        trackStop.style.animationDelay = '-' + startingTime;
    }

    active.style.animationPlayState = 'running'
    track.style.animationPlayState = 'running'
    trackStop.style.animationPlayState = 'running'
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

function getDateUTC() {
    let date = new Date();
    return new Date(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate(),
        date.getUTCHours(), date.getUTCMinutes(), date.getUTCSeconds())
}

let timeDifferenceWithServer = getDateUTC() - serverTime

setInterval(() => {
    if (!isTimerStarted)
        return

    let serverTime = getDateUTC() - timeDifferenceWithServer
    let diff = serverTime - timerStartDate
    let fullTimerDuration = timerEndDate - timerStartDate

    if (serverTime > timerEndDate && !endTimerFormSubmitted) {
        endTimerFormSubmitted = true
        document.forms["end-timer"].submit()
    }

    updateTimeLeft(fullTimerDuration - diff)
}, 100)

if (isTimerStarted) {
    let serverTime = getDateUTC() - timeDifferenceWithServer
    let diff = serverTime - timerStartDate
    let fullTimerDuration = timerEndDate - timerStartDate

    document.getElementById("slice-active").classList.remove('active-indicator-none')

    createProgressBar("slice", fullTimerDuration + 'ms', diff + 'ms')
}

document.getElementById("open-settings").addEventListener('click', (event) => {
    document.getElementById("settings").showModal();
})

function settingsErr() {
    document.getElementById("settings").showModal();
}