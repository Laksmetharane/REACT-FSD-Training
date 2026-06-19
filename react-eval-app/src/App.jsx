import {Route,Routes} from "react-router-dom"
import AddUser from "./components/AddUser"
import UserList from "./components/UserList"
import Navbar from "./Navbar"
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const App = ()=>{
  return(
    <div>
      <div>
        <Navbar/>
      </div>
      <ToastContainer/>
      <Routes>
        <Route path="/"element={<UserList/>}></Route>
        <Route path="/users"element={<UserList/>}></Route>
        <Route path="/add-user" element={<AddUser/>}></Route>
      </Routes>

    </div>
  )

}
export default App