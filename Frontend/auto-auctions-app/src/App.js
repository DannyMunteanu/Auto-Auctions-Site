import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom'
import AuthenticationPage from './pages/AuthenticationPage';

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<AuthenticationPage/>}/>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
