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
 * this function gets a name, id and GPA from the user
 * and sends it to `add_record()` which adds the data to `table#chart > tbody`
 */

/**
 * this function takes a name, id and GPA and transforms it into a `<tr>` which
 * gets appended to `table#chart > tbody`
 * @param {String} file the name of the student
 * @param {Number} probability the student's id
 * @param {String} type the student's GPA
 */
function add_record(file, probability, type) {
    /**
     * the smarter more inclined students might abstract the logic of `from_inputs()` into its own function
     * just like in this file, other than that
     * the students will have to modify their code to accept `Student` objects
     */

    /**
     * the `wrap()` function is optional and most students aren't going to have it
     */
    const data = [file, probability, type].map((el) => {
        return wrap("td", el);
    });
    const row = wrap("tr", data.join(""));

    /**
     * the students need to append the new row to the tbody element,
     * not the table element
     */
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

const URL = "http://localhost:8080/spamDetector-1.0/api/spam";

/**
 * anonymous function that executes on script load
 *
 * this is what the students are going to be tested on
 */
(function () {
    fetch(URL)
        .then((res) => res.json()) // `.json()` returns a promise, not data
        .then((data) => {

            console.log(`Loaded data from ${URL}: `, data);

            // using `in` will not work
            for (const spam of data["students"]) {
                // just a wrapper around `add_record()`
                from_spam(spam);
            }
        })
        .catch((err) => {
            console.log("something went wrong: " + err);
        });
})();
