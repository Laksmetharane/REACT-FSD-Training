import { useEffect, useState } from "react"
import axios from "axios"
import { toast } from "react-toastify"
import { useNavigate } from "react-router-dom"


const AddUser = () => {
    const navigate = useNavigate()
    const [user, setUser] = useState([])
    const [name, setName] = useState([])
    const [email, setEmail] = useState([])
    const [phone, setPhone] = useState([])
    const [comname, setComname] = useState([])
    const [successMsg, setSuccessMsg] = useState()
    const [errMsg, setErrMsg] = useState([])

    const posturl = 'https://jsonplaceholder.typicode.com/users'

    const PostApi = async (e) => {
        e.preventDefault()
        const body = {
            'name': name,
            'email': email,
            'phone': phone,
            'company': {
                name: comname
            }
        }
        console.log(body)

        try {
            const response = await axios.post(posturl, body)
            setUser(response.data)
            setName('')
            setEmail('')
            setPhone('')
            setComname('')
            setSuccessMsg("User Added Successfully")
            setErrMsg(undefined)
            toast.success("User Added Successfully 🚀", {
                theme: "colored",
                position: "top-right"
            });
            setTimeout(()=>{
                navigate("/users")
            },2000)
            
        }
        catch (err) {
            setErrMsg("Cannot Add User: " + err)
            setSuccessMsg(undefined)
            toast.error("Cannot Add User!", {
                position: "top-right",
                autoClose: 3000,
            });
        }

    }

    return (
        <div className="container py-5">
            <div className="row justify-content-center">
                <div className="col-lg-8">

                    <div className="text-center mb-4">
                        <h1 className="fw-bold text-primary">
                            Add New User
                        </h1>
                        <p className="text-muted">
                            User Management Dashboard
                        </p>
                    </div>

                    {
                        successMsg !== undefined ?
                            <div className="alert alert-success shadow-sm border-0">
                                {successMsg}
                            </div> : ""
                    }

                    {
                        errMsg !== undefined ?
                            <div className="alert alert-danger shadow-sm border-0">
                                {errMsg}
                            </div> : ""
                    }

                    <form
                        onSubmit={(e) => PostApi(e)}
                        className="card border-0 shadow-lg p-5 user-card"
                    >

                        <label className="form-label fw-semibold text-secondary">
                            Enter Name
                        </label>
                        <input
                            required
                            type="text"
                            className="form-control form-control-lg"
                            onChange={(e) => setName(e.target.value)}
                            value={name}
                        />

                        <br />
                        <br />

                        <label className="form-label fw-semibold text-secondary">
                            Enter Email
                        </label>
                        <input
                            required
                            type="text"
                            className="form-control form-control-lg"
                            onChange={(e) => setEmail(e.target.value)}
                            value={email}
                        />

                        <br />
                        <br />

                        <label className="form-label fw-semibold text-secondary">
                            Enter Your Mobile Number
                        </label>
                        <input
                            required
                            type="text"
                            className="form-control form-control-lg"
                            onChange={(e) => setPhone(e.target.value)}
                            value={phone}
                        />

                        <br />
                        <br />

                        <label className="form-label fw-semibold text-secondary">
                            Enter Company Name
                        </label>
                        <input
                            required
                            type="text"
                            className="form-control form-control-lg"
                            onChange={(e) => setComname(e.target.value)}
                            value={comname}
                        />

                        <br />
                        <br />

                        <input
                            type="submit"
                            value="Add User"
                            className="btn btn-primary btn-lg fw-bold shadow-sm"
                        />
                        
                    </form>

                </div>
            </div>
        </div>
    )
}
export default AddUser