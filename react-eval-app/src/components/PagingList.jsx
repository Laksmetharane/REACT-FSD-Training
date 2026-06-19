import { useState,useEffect } from "react"
import axios from "axios"
import { useSelector } from "react-redux"
import { useDispatch } from "react-redux"
import { getAll } from "../store/action/characterAction"

const PagingList = ()=>{
    const data = useSelector(state=>state.characters.characters)
    const [errMsg,setErrMsg]=useState([])

    const [currentPage,setCurrentPage]=useState(1)
    const [size,setSize]=useState(20)
    const totalPages = useSelector(state=>state.characters.totalPages)
    const [arr,setArray]=useState([])
    const dispatch = useDispatch()
    let count = 0;

    const geturl = 'https://rickandmortyapi.com/api/character/?page='

    useEffect(()=>{
        dispatch(getAll(currentPage))
    },[currentPage])

    return(
        <div>
            <table>
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Name</th>
                        <th scope="col">Status</th>
                        <th scope="col">Species</th>
                        <th scope="col">Origin Name</th>
                        <th scope="col">Location Name</th>

                    </tr>
                </thead>
                <tbody>
                    {
                        data.map((d,index)=>(
                            <tr key={index}>
                                <td>{d.id}</td>
                                <td>{d.name}</td>
                                <td>{d.status}</td>
                                <td>{d.species}</td>
                                <td>{d.origin.name}</td>
                                <td>{d.location.name}</td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
            <nav>
                <ul>
                    <li>
                        <button disabled={currentPage===1} onClick={()=>setCurrentPage(currentPage-1)}>Previous</button>
                    </li>
                    {
                        arr.map((_,index)=>(
                            <li key={index}>
                                <button onClick={()=>setCurrentPage(index+1)}>{index+1}</button>
                            </li>
                        ))
                    }
                    <li>
                        <button disabled ={currentPage===totalPages} onClick={()=>setCurrentPage(currentPage+1)}>Next</button>
                    </li>
                </ul>
            </nav>
        </div>
    )

}
export default PagingList