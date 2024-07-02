function setInputActive(inputName) {
    let input = document.getElementById(inputName);
    let placeholder = document.getElementById(inputName + '-placeholder')
    let field = document.getElementById(inputName + '-field')
    placeholder.classList.add('input-placeholder-active')
    field.classList.add('input-field-active')
    input.focus();
}

let inputs = document.getElementsByTagName('input')
for (let input of inputs) {
    input.addEventListener('focusin', (event) => {
        let id = event.target.id
        let placeholder = document.getElementById(id + '-placeholder')
        let field = document.getElementById(id + '-field')
        placeholder.classList.add('input-placeholder-active')
        field.classList.add('input-field-active')
    })

    input.addEventListener('focusout', (event) => {
        let id = event.target.id
        let placeholder = document.getElementById(id + '-placeholder')
        let field = document.getElementById(id + '-field')
        if (event.target.value === '') {
            placeholder.classList.remove('input-placeholder-active')
            field.classList.remove('input-field-active')
        }
    })
}