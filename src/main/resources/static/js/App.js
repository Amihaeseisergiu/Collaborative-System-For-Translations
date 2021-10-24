import React, { Component } from "react";
import ReactDOM from "react-dom";

export class App extends Component {
    render() {
        return (
            <div className="bg-blue-500">
                <h1>React</h1>
            </div>
    );
    }
}

export default App;

ReactDOM.render(<App />, document.querySelector("#app"));