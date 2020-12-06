from bs4 import BeautifulSoup

def transfer(html,out):
    fileIn = open(html,'rb')
    html = fileIn.read()
    fileOut = open(out,'wt')
    soup = BeautifulSoup(html,'html.parser')
    tableName = soup.find('table',class_='d2l-table d2l-grid d_gl').get('id')
    table = soup.find('table',attrs={'id':tableName})
    results = table.find_all('tr')
    print('Number of results', len(results))

    count = 0
    for result in results:
        if count == 0:
            count+=1
            continue
        #print(result)
        #print('\n')
        #name
        name = result.find('a',class_='d2l-link d2l-link-inline').get('title')
        name1 = str(name)
        name2 = name1[17:]
        print(name2,', ',end = '',file = fileOut)
        #email
        line = result.findAll('td',class_='d_gn')
        email = line[1].get_text()
        print(email, ', ', end = '',file=fileOut)
        #role
        role = result.find('td',class_='d_gn d2l-table-cell-last').get_text()
        print(role, file=fileOut)
        count+=1

    return soup
if __name__ == '__main__':
    transfer('ma261.html','listMa261.txt')
