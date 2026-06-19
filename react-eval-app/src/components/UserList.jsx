import axios from "axios"
import { useState, useEffect } from "react"
import { toast } from "react-toastify"

const UserList = () => {
    const [user, setUser] = useState([])
    const geturl = 'https://jsonplaceholder.typicode.com/users'
    const delurl = 'https://jsonplaceholder.typicode.com/users/'
    const [errMsg, setErrMsg] = useState([])
    const [delerrMsg, setDelErrMsg] = useState([])
    const [delsuccessMsg, setDelSuccessMsg] = useState([])

    useEffect(() => {
        const getAllApi = async () => {
            try {
                const response = await axios.get(geturl)
                setUser(response.data)
            }
            catch (err) {
                setErrMsg("Something is Error: " + err)
            }
        }
        getAllApi()
    }, [])



    const onDelete = async (id) => {
        try {
            const resp = await axios.delete(delurl + id)
            let temp = [...user].filter(u => u.id != id)
            setUser([...temp])
            setDelSuccessMsg("Successfully Deleted")
            console.log(delerrMsg)
            setDelErrMsg(undefined)
            toast.success("User Deleted Successfully")
        }
        catch (err) {
            setDelErrMsg("Cannot delete the User: " + err)
            setDelSuccessMsg(undefined)
        }
    }

    return (
        <div>
            <h1>User List</h1>
            <div className="container mt-4">
                <table className="table table-hover table-striped table-bordered shadow">
                    <thead className="table-dark">
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Name</th>
                            <th scope="col">Email</th>
                            <th scope="col">Phone</th>
                            <th scope="col">Company Name</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            user.map((u, index) => (
                                <tr key={index}>
                                    <td>{u.id}</td>
                                    <td>{u.name}</td>
                                    <td>{u.email}</td>
                                    <td>{u.phone}</td>
                                    <td>{u.company.name}</td>
                                    <td>
                                        <button
                                            className="btn btn-danger btn-sm fw-bold"
                                            onClick={() => onDelete(u.id)}
                                        >
                                            Delete
                                        </button>
                                    </td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>
        </div>
    )
}
export default UserList