// KohiShop Part 2 : Membership : database membership KohiShop
import java.util.ArrayList;

public class MemberManagement 
{
    private ArrayList<Membership> memberDatabase = new ArrayList<>();
    private Membership currentMember;

    public void autoAddMember(String nama)
    {
        currentMember = new Membership(nama);
        memberDatabase.add(currentMember);
        setMember(currentMember);
    }

    public ArrayList<Membership> getMemberDatabase() 
    {
        return memberDatabase;
    }

    public Membership getMember()
    {
        return currentMember;
    }

    public void setMember(Membership member)
    {
        this.currentMember = member;
    }
}
