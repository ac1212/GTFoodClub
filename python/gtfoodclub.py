import requests
from bs4 import BeautifulSoup
from datetime import datetime
import json

class GtEvent:
    def __init__(self):
        self.id = None
        self.title = ''
        self.time = []
        self.link = None
        self.is_all_day = False
        self.location = ''
        self.summary = ''
        self.contact = ''
    def __str__(self):
        return '\
        Event:\n\
            id:{}, link:{}\n\
            title: {}\n\
            times: {}\n\
            location: {}, summary: {}\n\
            contact: {}\n'.format(current_event.id,
            current_event.link,
            current_event.title,
            len(current_event.time),
            current_event.location,
            current_event.summary,
            current_event.contact)
    def toJSON(self):
        return json.dumps(self, default=lambda o: o.__dict__, sort_keys=True, indent=4)




def parse_datetime(str):
    #remove last colon
    str = str[:str.rfind(':')]+str[str.rfind(':')+1:]
    return datetime.strptime(str, '%Y-%m-%dT%H:%M:%S%z')

page = requests.get('http://www.calendar.gatech.edu/feeds/events.xml')
soup = BeautifulSoup(page.content, 'html.parser')
gt_events = []
for event in soup.find_all('item'):
    current_event = GtEvent()
    # get link and id
    link = event.link.next_sibling.rstrip()
    id = link[link.rindex('/')+1:]
    current_event.link = link
    current_event.id = id
    # get title
    current_event.title = event.title.string
    # get rest
    soup_description = BeautifulSoup(event.description.string, 'html.parser')
    #print(soup_description.prettify())
    # get times
    soup_date = soup_description.find('div', {'class':'field-name-field-date'})
    start_times = soup_date.find_all('span', {'class':'date-display-start'})
    end_times =   soup_date.find_all('span', {'class':'date-display-end'})
    dates =       soup_date.find_all('span', {'class':'date-display-single'})
    for idx in range(len(start_times)):
        start_time = parse_datetime(start_times[idx]['content'])
        end_time = parse_datetime(end_times[idx]['content'])
        current_event.time.append((start_time,end_time))
    for d in dates:
        if d.has_attr('content'):
            current_event.time.append(parse_datetime(d['content']))
    current_event.is_all_day = len(start_times)==0
    # get location
    soup_location = soup_description.find('div', {'class':'field-name-field-location'})
    current_event.location = soup_location.find('div',{'class':'field-item'}).string
    # get summary
    soup_summary = soup_description.find('div', {'class':'field-name-field-summary-sentence'})
    current_event.summary = soup_summary.find('div',{'class':'field-item'}).string
    # get contact
    soup_contact = soup_description.find('div', {'class':'field-name-field-contact'})
    if soup_contact is not None:
        for contact_tag in soup_contact.find_all('div',{'class':'field-item'}):
            for tag_inside_contact in contact_tag.find_all():
                if tag_inside_contact.string is not None:
                    current_event.contact = current_event.contact + tag_inside_contact.string + ' '
    print(current_event.toJSON())
    gt_events.append(current_event)