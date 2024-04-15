import grpc from 'k6/net/grpc';
import { check, sleep } from "k6";

let URL = 'localhost:9090';

let client = new grpc.Client();
client.load(['../src/main/proto'], 'APIBooks.proto');


export default () => {
    client.connect(URL, {
        plaintext: true
    })
    
    let dataTagOnly =
    {
        ISBN: '978-0000000000'
    }

    let data = {
        ISBN: '978-0000000000',
        Name: 'Booker',
        Language: 'es-MX',
        Descriptions: [
            {
                Text: 'Text1',
                Language: 'es-MX'
            }
        ]
    };

    let response = client.invoke('APIBooksRPC.APIBooksBookGRPC/AddNewBook', data);
    check(response, {
        'status is ok Add': (r) => r.status === grpc.StatusOK,
    });


    response = client.invoke('APIBooksRPC.APIBooksBookGRPC/GetBookByISBN', dataTagOnly);
    check(response, {
        'status is ok Get': (r) => r.status === grpc.StatusOK,
    });

    data = {
        ISBN: '978-0000000000',
        Name: 'Gambler',
        Language: 'es-MX',
        Descriptions: [
            {
                Text: 'Text1',
                Language: 'es-MX'
            }
        ]
    };

    response = client.invoke('APIBooksRPC.APIBooksBookGRPC/ModifyBook', data);
    check(response, {
        'status is ok after changing Name': (r) => r.status === grpc.StatusOK,
        'Name change success': (r) => r.message.Name === 'Gambler'
    });

    data = {
        ISBN: '978-0000000000',
        Name: 'Gambler',
        Language: 'en-GB',
        Descriptions: [
            {
                Text: 'Text1',
                Language: 'es-MX'
            }
        ]
    };

    response = client.invoke('APIBooksRPC.APIBooksBookGRPC/ModifyBook', data);
    check(response, {
        'status is ok after changing Language': (r) => r.status === grpc.StatusOK,
        'Language change success': (r) => r.message.Language === 'en-GB'
    });

    data = {
        ISBN: '978-0000000000',
        Name: 'Gambler',
        Language: 'en-GB',
        Descriptions: [
            {
                Text: 'Text2',
                Language: 'en-GB'
            }
        ]
    };

    response = client.invoke('APIBooksRPC.APIBooksBookGRPC/ModifyBook', data);
    check(response, {
        'status is ok after changing Descriptions': (r) => r.status === grpc.StatusOK,
        'Descriptions.Text change success': (r) => r.message.Descriptions[0].Text === 'Text2',
        'Descriptions.Language change success': (r) => r.message.Descriptions[0].Language === 'en-GB',
    });


    response = client.invoke('APIBooksRPC.APIBooksBookGRPC/DeleteBook', dataTagOnly);
    check(response, {
        'status is ok delete': (r) => r.status === grpc.StatusOK,
    });

    client.close()
}