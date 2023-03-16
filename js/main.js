/**
 * A student object
 * @typedef Spam
 * @property {String} file
 * @property {Number} probability
 * @property {String} type
 */

/**
 * this is a clean wrapper to add students to `table#chart > tbody`
 * @param {Spam} spam the student to add
 */
function from_spam(spam) {
    // adding it to the table
    add_record(spam.file, spam.probability, spam.type);
}

/**
 * this function takes a name, id and GPA and transforms it into a `<tr>` which
 * gets appended to `table#chart > tbody`
 * @param {String} file the name of the student
 * @param {Number} probability the student's id
 * @param {String} type the student's GPA
 */
function add_record(file, probability, type) {
    //gets the data from the json file
    //takes the data and puts it into a wrap to change it into tr information
    const data = [file, probability, type].map((el) => {
        return wrap("td", el);
    });
    const row = wrap("tr", data.join(""));
    //adds a new row into the chart
    document.getElementById("chart").getElementsByTagName("tbody")[0].innerHTML +=
        row;
}

/**
 * an optional function which aids in HTML tag wrapping
 * @param {String} tag the HTML tag
 * @param {String} data something to wrap
 * @returns {String} `<tag>data</tag>`
 */
function wrap(tag, data) {
    return `<${tag}>${data}</${tag}>`;
}
//creating the url
const URL = "http://localhost:8080/spamDetector-1.0/api/spam";

(function () {
    fetch(URL)
        .then((res) => res.json()) // `.json()` returns a promise, not data
        .then((data) => {
            //getting data from the url
            console.log(`Loaded data from ${URL}: `, data);

            for (const spam of data["spam"]) {
                from_spam(spam);
            }
        })
        //catch an err and output something went wrong with the error
        .catch((err) => {
            console.log("something went wrong: " + err);
        });
})();
