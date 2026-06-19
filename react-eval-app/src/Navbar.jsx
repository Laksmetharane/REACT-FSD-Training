import { NavLink } from "react-router-dom";

function Navbar() {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container">

        <div>
          <NavLink className="btn btn-primary me-2" to="/users">
            User List
          </NavLink>
          <br/>
          <br/>

          <NavLink className="btn btn-success" to="/add-user">
            Add User
          </NavLink>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;