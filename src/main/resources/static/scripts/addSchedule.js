function openAddSchedule() {
    var element = document.getElementById("addSchedule");
    if (element.classList.contains("closeAddSchedule")) {
        element.classList.remove("closeAddSchedule");
        element.classList.add("openAddSchedule");
    }
}
function closeAddSchedule() {
    var element = document.getElementById("addSchedule");
    element.classList.toggle("closeAddSchedule");
}