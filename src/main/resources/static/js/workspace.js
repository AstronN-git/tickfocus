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

setInterval(() => {
    if (!isTimerStarted)
        return

    let date = new Date()
    let dateUTC = new Date(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate(),
        date.getUTCHours(), date.getUTCMinutes(), date.getUTCSeconds())
    let diff = dateUTC - timerStartDate
    let fullTimerDuration = timerEndDate - timerStartDate

    if (dateUTC > timerEndDate) {
        if (!endTimerFormSubmitted) {
            document.forms["end-timer"].submit()
            endTimerFormSubmitted = true
        }
    }

    updateTimeLeft(fullTimerDuration - diff)
}, 100)

if (isTimerStarted) {
    let fullTimerDuration = timerEndDate - timerStartDate
    let date = new Date()
    let dateUTC = new Date(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate(),
        date.getUTCHours(), date.getUTCMinutes(), date.getUTCSeconds())
    let diff = dateUTC - timerStartDate

    document.getElementById("slice-active").classList.remove('active-indicator-none')

    createProgressBar("slice", fullTimerDuration + 'ms', diff + 'ms')
}

document.getElementById("open-settings").addEventListener('click', (event) => {
    document.getElementById("settings").showModal();
})

function settingsErr() {
    document.getElementById("settings").showModal();
}