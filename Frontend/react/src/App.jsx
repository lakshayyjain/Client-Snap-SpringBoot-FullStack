import {
    Wrap,
    WrapItem,
    Spinner,
    Text} from '@chakra-ui/react'
import SidebarWithHeader from '@/components/shared/Sidebar.jsx'
import {useEffect, useState} from "react";
import {getCustomers} from "@/services/client.js";
import CardWithImage from "@/components/Card.jsx";
import DrawerForm from "@/components/DrawerForm.jsx";
import {errorNotification} from "@/services/notification.js";

const App = () => {

    const [customers, setCustomers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const fetchCustomers = () => {
        setLoading(true);
        getCustomers().then(res => {
            setCustomers(res.data);
            // console.log(res)
        }).catch(err => {
            setError(err.response.data.message);
            errorNotification(
                err.code,
                err.response.data.message
            )
        }).finally(() => {
            setLoading(false);
        })
    };

    useEffect(() => {
        setLoading(true);
        getCustomers().then(res => {
            setCustomers(res.data);
            // console.log(res)
        }).catch(err => {
            console.log(err)
        }).finally(() => {
            setLoading(false);
        })
    }, []);

    if(loading){
        return (
            <SidebarWithHeader>
                <Spinner
                    thickness='4px'
                    speed='0.65s'
                    emptyColor='gray.200'
                    color='blue.500'
                    size='xl'
                />
            </SidebarWithHeader>
        )
    }

    if(error){
        return (
            <SidebarWithHeader>
                <DrawerForm fetchCustomers={fetchCustomers} />
                <Text mt={"20px"} ml={"20px"}>Oops there was an error</Text>
            </SidebarWithHeader>
        )
    }

    if(customers.length <= 0){
        return (
            <SidebarWithHeader>
                <DrawerForm fetchCustomers={fetchCustomers} />
                <Text mt={"20px"} ml={"20px"}>No Customers Found</Text>
            </SidebarWithHeader>
        )
    }

    return (
        <SidebarWithHeader>
            <DrawerForm fetchCustomers={fetchCustomers} />
            <Wrap spacing={"30px"} justify='center'>
                {customers.map((customer, index) => (
                    <WrapItem key={index}>
                        <CardWithImage {...customer}/>
                    </WrapItem>
                ))}
            </Wrap>
        </SidebarWithHeader>
    )
};

export default App