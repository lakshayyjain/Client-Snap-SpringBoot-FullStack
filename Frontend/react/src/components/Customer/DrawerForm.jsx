import {
    Button,
    Drawer, DrawerBody,
    DrawerCloseButton,
    DrawerContent, DrawerFooter,
    DrawerHeader,
    DrawerOverlay, Input,
    useDisclosure
} from "@chakra-ui/react";
import CreateCustomerForm from "@/components/Customer/CreateCustomerForm.jsx";

const AddIcon = () => "+";
const CloseIcon = () => "X";

const DrawerForm = ( {fetchCustomers} ) => {
    const { isOpen, onOpen, onClose } = useDisclosure()
    return <>
        <Button
            leftIcon={<AddIcon/>}
            onClick={() => {
                onOpen();
            }}
            colorScheme="teal"
        >
            Create Customer
        </Button>
    <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
        <DrawerOverlay />
        <DrawerContent>
            <DrawerCloseButton />
            <DrawerHeader>Create New Customer</DrawerHeader>

            <DrawerBody>
                <CreateCustomerForm
                    fetchCustomers={fetchCustomers}/>
            </DrawerBody>

            <DrawerFooter>
                <Button
                    leftIcon={<CloseIcon/>}
                    onClick={() => {
                        onClose();
                    }}
                    colorScheme="teal"
                >
                    Close
                </Button>
            </DrawerFooter>
        </DrawerContent>
    </Drawer>
    </>
};

export default DrawerForm;